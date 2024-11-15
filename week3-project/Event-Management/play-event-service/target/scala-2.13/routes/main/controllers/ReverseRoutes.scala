// @GENERATOR:play-routes-compiler
// @SOURCE:conf/routes

import play.api.mvc.Call


import _root_.controllers.Assets.Asset

// @LINE:7
package controllers {

  // @LINE:10
  class ReverseAssets(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:10
    def versioned(file:Asset): Call = {
      implicit lazy val _rrc = new play.core.routing.ReverseRouteContext(Map(("path", "/public"))); _rrc
      Call("GET", _prefix + { _defaultPrefix } + "assets/" + implicitly[play.api.mvc.PathBindable[Asset]].unbind("file", file))
    }
  
  }

  // @LINE:12
  class ReverseEventController(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:14
    def updateEvent(eventId:Long): Call = {
      
      Call("PUT", _prefix + { _defaultPrefix } + "event/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[Long]].unbind("eventId", eventId)))
    }
  
    // @LINE:12
    def createEvent(): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "event")
    }
  
    // @LINE:15
    def listEvents(eventType:Option[String], status:Option[String], eventDate:Option[String], slotNumber:Option[Int]): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "events" + play.core.routing.queryString(List(Some(implicitly[play.api.mvc.QueryStringBindable[Option[String]]].unbind("eventType", eventType)), Some(implicitly[play.api.mvc.QueryStringBindable[Option[String]]].unbind("status", status)), Some(implicitly[play.api.mvc.QueryStringBindable[Option[String]]].unbind("eventDate", eventDate)), Some(implicitly[play.api.mvc.QueryStringBindable[Option[Int]]].unbind("slotNumber", slotNumber)))))
    }
  
    // @LINE:16
    def getTasksForEventId(eventId:Long): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "events/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[Long]].unbind("eventId", eventId)) + "/tasks")
    }
  
    // @LINE:13
    def getEventById(eventId:Long): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "event/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[Long]].unbind("eventId", eventId)))
    }
  
  }

  // @LINE:7
  class ReverseHomeController(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:7
    def index(): Call = {
      
      Call("GET", _prefix)
    }
  
  }

  // @LINE:28
  class ReverseIssueController(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:28
    def create(): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "issue")
    }
  
  }

  // @LINE:24
  class ReverseTeamController(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:24
    def registerTeam(): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "team")
    }
  
    // @LINE:25
    def getTeamDetails(teamId:Long): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "team/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[Long]].unbind("teamId", teamId)))
    }
  
    // @LINE:26
    def listTeams(teamType:Option[String]): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "teams" + play.core.routing.queryString(List(Some(implicitly[play.api.mvc.QueryStringBindable[Option[String]]].unbind("teamType", teamType)))))
    }
  
  }

  // @LINE:19
  class ReverseTaskController(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:19
    def createTask(): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "task")
    }
  
    // @LINE:20
    def getTaskById(taskId:Long): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "task/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[Long]].unbind("taskId", taskId)))
    }
  
    // @LINE:21
    def updateTaskStatus(taskId:Long, status:String): Call = {
      
      Call("PUT", _prefix + { _defaultPrefix } + "task/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[Long]].unbind("taskId", taskId)) + play.core.routing.queryString(List(Some(implicitly[play.api.mvc.QueryStringBindable[String]].unbind("status", status)))))
    }
  
    // @LINE:22
    def assignTasks(): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "task/assign")
    }
  
  }


}
