package com.ttnd.linksharing

import com.ttnd.linksharing.Enum.Seriousness
import com.ttnd.linksharing.Enum.Visiblity
import com.ttnd.linksharing.VO.TopicVo
import com.ttnd.linksharing.Resource

class Topic {


    String name
    User createdBy
    Date dateCreated
    Date lastUpdated

    Visiblity visiblity
    static transients = ['subscribedUsers']
    static hasMany = [resources: Resource, subscription: Subscription]

    static constraints = {

        name(nullable: false, blank: false, unique: 'createdBy')

        createdBy(nullable: false)


    }
    static mapping = {
        sort "name"
    }

    def afterInsert() {
        Topic.withNewSession {
            Subscription subscription = new Subscription(topic: this, user: this.createdBy, seriousness: Seriousness.VERY_SERIOUS).save()
            //this.createdBy.addToSubscriptions(subscription)
            log.info(" subscription saved  ")
        } //this.addToSubscription(subscription)

    }

    static List<TopicVo> trendingTopics() {
        List result = Resource.createCriteria().list([sort:'dateCreated',max:3]) {

            projections {
                createAlias('topic', 't')
                groupProperty('t.id')
                property('t.name')
                property('t.visiblity')
                count('id')
                property('t.createdBy')
            }
            //order("r.id", "desc")
            eq('t.visiblity', Visiblity.PUBLIC)
            order("t.name", "desc")
            maxResults 5
        }
        println("=========================${result}")
        List<TopicVo> topicVo = []
        result.each {
            topicVo.add(new TopicVo(id: it[0], name: it[1], visibility: it[2], count: it[3], createdBy: it[4]))
        }
        topicVo


    }

    List<Topic> getsubscribedUser() {
        List<Topic> result = Subscription.createCriteria().list() {


            projections {
                property('user')
            }


            eq('topic.id', this.id)
        }

        result

    }

     Boolean isPublic(Long id) {
        Topic topic = Topic.findById(id)

        if (topic.visiblity == Visiblity.PUBLIC)
            true
        else
            false
    }

     Boolean canViewBy(Long id) {
        Topic topic = Topic.findById(id)
      List<User> user= topic.subscribedUser

        if (isPublic(id) || this.contains(user)||user.admin)
            true
        else
            false

    }


    String toString() {
        "This is topic $name"
    }


}
