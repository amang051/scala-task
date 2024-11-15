// @GENERATOR:play-routes-compiler
// @SOURCE:conf/routes

import play.api.routing.JavaScriptReverseRoute


import _root_.controllers.Assets.Asset

// @LINE:7
package controllers.javascript {

  // @LINE:10
  class ReverseAssets(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:10
    def versioned: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.Assets.versioned",
      """
        function(file1) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "assets/" + (""" + implicitly[play.api.mvc.PathBindable[Asset]].javascriptUnbind + """)("file", file1)})
        }
      """
    )
  
  }

  // @LINE:12
  class ReverseEventController(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:14
    def updateEvent: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.EventController.updateEvent",
      """
        function(eventId0) {
          return _wA({method:"PUT", url:"""" + _prefix + { _defaultPrefix } + """" + "event/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[Long]].javascriptUnbind + """)("eventId", eventId0))})
        }
      """
    )
  
    // @LINE:12
    def createEvent: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.EventController.createEvent",
      """
        function() {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "event"})
        }
      """
    )
  
    // @LINE:15
    def listEvents: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.EventController.listEvents",
      """
        function(eventType0,status1,eventDate2,slotNumber3) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "events" + _qS([(""" + implicitly[play.api.mvc.QueryStringBindable[Option[String]]].javascriptUnbind + """)("eventType", eventType0), (""" + implicitly[play.api.mvc.QueryStringBindable[Option[String]]].javascriptUnbind + """)("status", status1), (""" + implicitly[play.api.mvc.QueryStringBindable[Option[String]]].javascriptUnbind + """)("eventDate", eventDate2), (""" + implicitly[play.api.mvc.QueryStringBindable[Option[Int]]].javascriptUnbind + """)("slotNumber", slotNumber3)])})
        }
      """
    )
  
    // @LINE:16
    def getTasksForEventId: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.EventController.getTasksForEventId",
      """
        function(eventId0) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "events/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[Long]].javascriptUnbind + """)("eventId", eventId0)) + "/tasks"})
        }
      """
    )
  
    // @LINE:13
    def getEventById: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.EventController.getEventById",
      """
        function(eventId0) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "event/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[Long]].javascriptUnbind + """)("eventId", eventId0))})
        }
      """
    )
  
  }

  // @LINE:7
  class ReverseHomeController(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:7
    def index: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.HomeController.index",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + """"})
        }
      """
    )
  
  }

  // @LINE:28
  class ReverseIssueController(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:28
    def create: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.IssueController.create",
      """
        function() {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "issue"})
        }
      """
    )
  
  }

  // @LINE:24
  class ReverseTeamController(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:24
    def registerTeam: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.TeamController.registerTeam",
      """
        function() {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "team"})
        }
      """
    )
  
    // @LINE:25
    def getTeamDetails: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.TeamController.getTeamDetails",
      """
        function(teamId0) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "team/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[Long]].javascriptUnbind + """)("teamId", teamId0))})
        }
      """
    )
  
    // @LINE:26
    def listTeams: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.TeamController.listTeams",
      """
        function(teamType0) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "teams" + _qS([(""" + implicitly[play.api.mvc.QueryStringBindable[Option[String]]].javascriptUnbind + """)("teamType", teamType0)])})
        }
      """
    )
  
  }

  // @LINE:19
  class ReverseTaskController(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:19
    def createTask: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.TaskController.createTask",
      """
        function() {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "task"})
        }
      """
    )
  
    // @LINE:20
    def getTaskById: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.TaskController.getTaskById",
      """
        function(taskId0) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "task/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[Long]].javascriptUnbind + """)("taskId", taskId0))})
        }
      """
    )
  
    // @LINE:21
    def updateTaskStatus: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.TaskController.updateTaskStatus",
      """
        function(taskId0,status1) {
          return _wA({method:"PUT", url:"""" + _prefix + { _defaultPrefix } + """" + "task/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[Long]].javascriptUnbind + """)("taskId", taskId0)) + _qS([(""" + implicitly[play.api.mvc.QueryStringBindable[String]].javascriptUnbind + """)("status", status1)])})
        }
      """
    )
  
    // @LINE:22
    def assignTasks: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.TaskController.assignTasks",
      """
        function() {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "task/assign"})
        }
      """
    )
  
  }


}
