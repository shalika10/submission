package com.ttnd.linksharing

class LoginController {

    def index() {
        if (session.user) {
            forward(controller: 'User', action: 'index')
        } else {
           List result = ResourceRating.showRecentPost()

            List<Resource> resources1 = Resource.getAll(result)


//            List<Resource> resources = Resource.getAll(result1)
//            println"--------------- ${resources.size()}"
//
//            params.max = params.max?:2
//            params.offset = params.offset?:0
//            List result1 = ResourceRating.showTopPost(params)
            render (view: 'home', model: [ resources1:resources1])
        }
    }

//    def topPost() {
//        if (!session.user) {
//
//            params.max = params.max?:2
//            params.offset = params.offset?:0
//            List result = ResourceRating.showTopPost(params)
//
//            List<Resource> resources = Resource.getAll(result)
//            //render "$resources"
//            // render (view:'topPost',model: [resources:resources] )
//           render (template: 'topPost', model: [resources: resources, resourceCount: 5])
//        } else render "hioiiii "
//    }

//    def recentPost() {
//        if (!session.user) {
//            List result = ResourceRating.showRecentPost()
//
//            List<Resource> resources1 = Resource.getAll(result)
//            //render "$resources"
//            render(view: 'recentPost', model: [resources1: resources1])
//            //redirect(action:'index',controller:'login')
//        } else render "hioiiii "
//
//
//    }

    def login(String userName, String password) {
        User user = User.findByUserNameAndPassword(userName, password)
        if (user) {
            if (user.active) {
                session.user = user
                redirect(controller: 'user', action: 'index')
            } else
                flash.message = "Your accoutn is not active"

        } else {
            flash.message = "User not found"
            render flash.message
        }
    }

    def logout() {
        session.invalidate()
        redirect(controller: 'Login', action: 'index')

        render "Logged out"
    }

    def forgotPassword()

    {

    }
}
