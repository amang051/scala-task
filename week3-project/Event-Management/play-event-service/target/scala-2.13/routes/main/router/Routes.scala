// @GENERATOR:play-routes-compiler
// @SOURCE:conf/routes

package router

import play.core.routing._
import play.core.routing.HandlerInvokerFactory._

import play.api.mvc._

import _root_.controllers.Assets.Asset

class Routes(
  override val errorHandler: play.api.http.HttpErrorHandler, 
  // @LINE:7
  HomeController_4: controllers.HomeController,
  // @LINE:10
  Assets_2: controllers.Assets,
  // @LINE:12
  EventController_0: controllers.EventController,
  // @LINE:19
  TaskController_5: controllers.TaskController,
  // @LINE:24
  TeamController_3: controllers.TeamController,
  // @LINE:28
  IssueController_1: controllers.IssueController,
  val prefix: String
) extends GeneratedRouter {

  @javax.inject.Inject()
  def this(errorHandler: play.api.http.HttpErrorHandler,
    // @LINE:7
    HomeController_4: controllers.HomeController,
    // @LINE:10
    Assets_2: controllers.Assets,
    // @LINE:12
    EventController_0: controllers.EventController,
    // @LINE:19
    TaskController_5: controllers.TaskController,
    // @LINE:24
    TeamController_3: controllers.TeamController,
    // @LINE:28
    IssueController_1: controllers.IssueController
  ) = this(errorHandler, HomeController_4, Assets_2, EventController_0, TaskController_5, TeamController_3, IssueController_1, "/")

  def withPrefix(addPrefix: String): Routes = {
    val prefix = play.api.routing.Router.concatPrefix(addPrefix, this.prefix)
    router.RoutesPrefix.setPrefix(prefix)
    new Routes(errorHandler, HomeController_4, Assets_2, EventController_0, TaskController_5, TeamController_3, IssueController_1, prefix)
  }

  private val defaultPrefix: String = {
    if (this.prefix.endsWith("/")) "" else "/"
  }

  def documentation = List(
    ("""GET""", this.prefix, """controllers.HomeController.index()"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """assets/""" + "$" + """file<.+>""", """controllers.Assets.versioned(path:String = "/public", file:Asset)"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """event""", """controllers.EventController.createEvent()"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """event/""" + "$" + """eventId<[^/]+>""", """controllers.EventController.getEventById(eventId:Long)"""),
    ("""PUT""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """event/""" + "$" + """eventId<[^/]+>""", """controllers.EventController.updateEvent(eventId:Long)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """events""", """controllers.EventController.listEvents(eventType:Option[String], status:Option[String], eventDate:Option[String], slotNumber:Option[Int])"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """events/""" + "$" + """eventId<[^/]+>/tasks""", """controllers.EventController.getTasksForEventId(eventId:Long)"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """task""", """controllers.TaskController.createTask()"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """task/""" + "$" + """taskId<[^/]+>""", """controllers.TaskController.getTaskById(taskId:Long)"""),
    ("""PUT""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """task/""" + "$" + """taskId<[^/]+>""", """controllers.TaskController.updateTaskStatus(taskId:Long, status:String)"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """task/assign""", """controllers.TaskController.assignTasks()"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """team""", """controllers.TeamController.registerTeam()"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """team/""" + "$" + """teamId<[^/]+>""", """controllers.TeamController.getTeamDetails(teamId:Long)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """teams""", """controllers.TeamController.listTeams(teamType:Option[String])"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """issue""", """controllers.IssueController.create()"""),
    Nil
  ).foldLeft(Seq.empty[(String, String, String)]) { (s,e) => e.asInstanceOf[Any] match {
    case r @ (_,_,_) => s :+ r.asInstanceOf[(String, String, String)]
    case l => s ++ l.asInstanceOf[List[(String, String, String)]]
  }}


  // @LINE:7
  private lazy val controllers_HomeController_index0_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix)))
  )
  private lazy val controllers_HomeController_index0_invoker = createInvoker(
    HomeController_4.index(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.HomeController",
      "index",
      Nil,
      "GET",
      this.prefix + """""",
      """ An example controller showing a sample home page""",
      Seq()
    )
  )

  // @LINE:10
  private lazy val controllers_Assets_versioned1_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("assets/"), DynamicPart("file", """.+""", encodeable=false)))
  )
  private lazy val controllers_Assets_versioned1_invoker = createInvoker(
    Assets_2.versioned(fakeValue[String], fakeValue[Asset]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.Assets",
      "versioned",
      Seq(classOf[String], classOf[Asset]),
      "GET",
      this.prefix + """assets/""" + "$" + """file<.+>""",
      """ Map static resources from the /public folder to the /assets URL path""",
      Seq()
    )
  )

  // @LINE:12
  private lazy val controllers_EventController_createEvent2_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("event")))
  )
  private lazy val controllers_EventController_createEvent2_invoker = createInvoker(
    EventController_0.createEvent(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.EventController",
      "createEvent",
      Nil,
      "POST",
      this.prefix + """event""",
      """""",
      Seq()
    )
  )

  // @LINE:13
  private lazy val controllers_EventController_getEventById3_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("event/"), DynamicPart("eventId", """[^/]+""", encodeable=true)))
  )
  private lazy val controllers_EventController_getEventById3_invoker = createInvoker(
    EventController_0.getEventById(fakeValue[Long]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.EventController",
      "getEventById",
      Seq(classOf[Long]),
      "GET",
      this.prefix + """event/""" + "$" + """eventId<[^/]+>""",
      """""",
      Seq()
    )
  )

  // @LINE:14
  private lazy val controllers_EventController_updateEvent4_route = Route("PUT",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("event/"), DynamicPart("eventId", """[^/]+""", encodeable=true)))
  )
  private lazy val controllers_EventController_updateEvent4_invoker = createInvoker(
    EventController_0.updateEvent(fakeValue[Long]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.EventController",
      "updateEvent",
      Seq(classOf[Long]),
      "PUT",
      this.prefix + """event/""" + "$" + """eventId<[^/]+>""",
      """""",
      Seq()
    )
  )

  // @LINE:15
  private lazy val controllers_EventController_listEvents5_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("events")))
  )
  private lazy val controllers_EventController_listEvents5_invoker = createInvoker(
    EventController_0.listEvents(fakeValue[Option[String]], fakeValue[Option[String]], fakeValue[Option[String]], fakeValue[Option[Int]]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.EventController",
      "listEvents",
      Seq(classOf[Option[String]], classOf[Option[String]], classOf[Option[String]], classOf[Option[Int]]),
      "GET",
      this.prefix + """events""",
      """""",
      Seq()
    )
  )

  // @LINE:16
  private lazy val controllers_EventController_getTasksForEventId6_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("events/"), DynamicPart("eventId", """[^/]+""", encodeable=true), StaticPart("/tasks")))
  )
  private lazy val controllers_EventController_getTasksForEventId6_invoker = createInvoker(
    EventController_0.getTasksForEventId(fakeValue[Long]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.EventController",
      "getTasksForEventId",
      Seq(classOf[Long]),
      "GET",
      this.prefix + """events/""" + "$" + """eventId<[^/]+>/tasks""",
      """""",
      Seq()
    )
  )

  // @LINE:19
  private lazy val controllers_TaskController_createTask7_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("task")))
  )
  private lazy val controllers_TaskController_createTask7_invoker = createInvoker(
    TaskController_5.createTask(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.TaskController",
      "createTask",
      Nil,
      "POST",
      this.prefix + """task""",
      """""",
      Seq()
    )
  )

  // @LINE:20
  private lazy val controllers_TaskController_getTaskById8_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("task/"), DynamicPart("taskId", """[^/]+""", encodeable=true)))
  )
  private lazy val controllers_TaskController_getTaskById8_invoker = createInvoker(
    TaskController_5.getTaskById(fakeValue[Long]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.TaskController",
      "getTaskById",
      Seq(classOf[Long]),
      "GET",
      this.prefix + """task/""" + "$" + """taskId<[^/]+>""",
      """""",
      Seq()
    )
  )

  // @LINE:21
  private lazy val controllers_TaskController_updateTaskStatus9_route = Route("PUT",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("task/"), DynamicPart("taskId", """[^/]+""", encodeable=true)))
  )
  private lazy val controllers_TaskController_updateTaskStatus9_invoker = createInvoker(
    TaskController_5.updateTaskStatus(fakeValue[Long], fakeValue[String]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.TaskController",
      "updateTaskStatus",
      Seq(classOf[Long], classOf[String]),
      "PUT",
      this.prefix + """task/""" + "$" + """taskId<[^/]+>""",
      """""",
      Seq()
    )
  )

  // @LINE:22
  private lazy val controllers_TaskController_assignTasks10_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("task/assign")))
  )
  private lazy val controllers_TaskController_assignTasks10_invoker = createInvoker(
    TaskController_5.assignTasks(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.TaskController",
      "assignTasks",
      Nil,
      "POST",
      this.prefix + """task/assign""",
      """""",
      Seq()
    )
  )

  // @LINE:24
  private lazy val controllers_TeamController_registerTeam11_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("team")))
  )
  private lazy val controllers_TeamController_registerTeam11_invoker = createInvoker(
    TeamController_3.registerTeam(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.TeamController",
      "registerTeam",
      Nil,
      "POST",
      this.prefix + """team""",
      """""",
      Seq()
    )
  )

  // @LINE:25
  private lazy val controllers_TeamController_getTeamDetails12_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("team/"), DynamicPart("teamId", """[^/]+""", encodeable=true)))
  )
  private lazy val controllers_TeamController_getTeamDetails12_invoker = createInvoker(
    TeamController_3.getTeamDetails(fakeValue[Long]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.TeamController",
      "getTeamDetails",
      Seq(classOf[Long]),
      "GET",
      this.prefix + """team/""" + "$" + """teamId<[^/]+>""",
      """""",
      Seq()
    )
  )

  // @LINE:26
  private lazy val controllers_TeamController_listTeams13_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("teams")))
  )
  private lazy val controllers_TeamController_listTeams13_invoker = createInvoker(
    TeamController_3.listTeams(fakeValue[Option[String]]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.TeamController",
      "listTeams",
      Seq(classOf[Option[String]]),
      "GET",
      this.prefix + """teams""",
      """""",
      Seq()
    )
  )

  // @LINE:28
  private lazy val controllers_IssueController_create14_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("issue")))
  )
  private lazy val controllers_IssueController_create14_invoker = createInvoker(
    IssueController_1.create(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.IssueController",
      "create",
      Nil,
      "POST",
      this.prefix + """issue""",
      """""",
      Seq()
    )
  )


  def routes: PartialFunction[RequestHeader, Handler] = {
  
    // @LINE:7
    case controllers_HomeController_index0_route(params@_) =>
      call { 
        controllers_HomeController_index0_invoker.call(HomeController_4.index())
      }
  
    // @LINE:10
    case controllers_Assets_versioned1_route(params@_) =>
      call(Param[String]("path", Right("/public")), params.fromPath[Asset]("file", None)) { (path, file) =>
        controllers_Assets_versioned1_invoker.call(Assets_2.versioned(path, file))
      }
  
    // @LINE:12
    case controllers_EventController_createEvent2_route(params@_) =>
      call { 
        controllers_EventController_createEvent2_invoker.call(EventController_0.createEvent())
      }
  
    // @LINE:13
    case controllers_EventController_getEventById3_route(params@_) =>
      call(params.fromPath[Long]("eventId", None)) { (eventId) =>
        controllers_EventController_getEventById3_invoker.call(EventController_0.getEventById(eventId))
      }
  
    // @LINE:14
    case controllers_EventController_updateEvent4_route(params@_) =>
      call(params.fromPath[Long]("eventId", None)) { (eventId) =>
        controllers_EventController_updateEvent4_invoker.call(EventController_0.updateEvent(eventId))
      }
  
    // @LINE:15
    case controllers_EventController_listEvents5_route(params@_) =>
      call(params.fromQuery[Option[String]]("eventType", None), params.fromQuery[Option[String]]("status", None), params.fromQuery[Option[String]]("eventDate", None), params.fromQuery[Option[Int]]("slotNumber", None)) { (eventType, status, eventDate, slotNumber) =>
        controllers_EventController_listEvents5_invoker.call(EventController_0.listEvents(eventType, status, eventDate, slotNumber))
      }
  
    // @LINE:16
    case controllers_EventController_getTasksForEventId6_route(params@_) =>
      call(params.fromPath[Long]("eventId", None)) { (eventId) =>
        controllers_EventController_getTasksForEventId6_invoker.call(EventController_0.getTasksForEventId(eventId))
      }
  
    // @LINE:19
    case controllers_TaskController_createTask7_route(params@_) =>
      call { 
        controllers_TaskController_createTask7_invoker.call(TaskController_5.createTask())
      }
  
    // @LINE:20
    case controllers_TaskController_getTaskById8_route(params@_) =>
      call(params.fromPath[Long]("taskId", None)) { (taskId) =>
        controllers_TaskController_getTaskById8_invoker.call(TaskController_5.getTaskById(taskId))
      }
  
    // @LINE:21
    case controllers_TaskController_updateTaskStatus9_route(params@_) =>
      call(params.fromPath[Long]("taskId", None), params.fromQuery[String]("status", None)) { (taskId, status) =>
        controllers_TaskController_updateTaskStatus9_invoker.call(TaskController_5.updateTaskStatus(taskId, status))
      }
  
    // @LINE:22
    case controllers_TaskController_assignTasks10_route(params@_) =>
      call { 
        controllers_TaskController_assignTasks10_invoker.call(TaskController_5.assignTasks())
      }
  
    // @LINE:24
    case controllers_TeamController_registerTeam11_route(params@_) =>
      call { 
        controllers_TeamController_registerTeam11_invoker.call(TeamController_3.registerTeam())
      }
  
    // @LINE:25
    case controllers_TeamController_getTeamDetails12_route(params@_) =>
      call(params.fromPath[Long]("teamId", None)) { (teamId) =>
        controllers_TeamController_getTeamDetails12_invoker.call(TeamController_3.getTeamDetails(teamId))
      }
  
    // @LINE:26
    case controllers_TeamController_listTeams13_route(params@_) =>
      call(params.fromQuery[Option[String]]("teamType", None)) { (teamType) =>
        controllers_TeamController_listTeams13_invoker.call(TeamController_3.listTeams(teamType))
      }
  
    // @LINE:28
    case controllers_IssueController_create14_route(params@_) =>
      call { 
        controllers_IssueController_create14_invoker.call(IssueController_1.create())
      }
  }
}
