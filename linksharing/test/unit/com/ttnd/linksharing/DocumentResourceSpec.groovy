package com.ttnd.linksharing

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(DocumentResource)
class DocumentResourceSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "test something"() {void "test link"() {
        given:
        LinkResource link =new LinkResource(url:url1)

        when:
        Boolean result=link.validate()
        then:
        result==valid
        where:
        url1|valid
        "com.linksharing.UtilController"|true



    }
    }
}
