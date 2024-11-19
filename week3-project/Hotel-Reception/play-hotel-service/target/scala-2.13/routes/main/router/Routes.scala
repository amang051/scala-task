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
  // @LINE:9
  RoomController_3: controllers.RoomController,
  // @LINE:11
  GuestController_0: controllers.GuestController,
  // @LINE:14
  BookingDetailsController_2: controllers.BookingDetailsController,
  // @LINE:17
  Assets_1: controllers.Assets,
  val prefix: String
) extends GeneratedRouter {

  @javax.inject.Inject()
  def this(errorHandler: play.api.http.HttpErrorHandler,
    // @LINE:7
    HomeController_4: controllers.HomeController,
    // @LINE:9
    RoomController_3: controllers.RoomController,
    // @LINE:11
    GuestController_0: controllers.GuestController,
    // @LINE:14
    BookingDetailsController_2: controllers.BookingDetailsController,
    // @LINE:17
    Assets_1: controllers.Assets
  ) = this(errorHandler, HomeController_4, RoomController_3, GuestController_0, BookingDetailsController_2, Assets_1, "/")

  def withPrefix(addPrefix: String): Routes = {
    val prefix = play.api.routing.Router.concatPrefix(addPrefix, this.prefix)
    router.RoutesPrefix.setPrefix(prefix)
    new Routes(errorHandler, HomeController_4, RoomController_3, GuestController_0, BookingDetailsController_2, Assets_1, prefix)
  }

  private val defaultPrefix: String = {
    if (this.prefix.endsWith("/")) "" else "/"
  }

  def documentation = List(
    ("""GET""", this.prefix, """controllers.HomeController.index()"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """room""", """controllers.RoomController.create()"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """guest""", """controllers.GuestController.create()"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """currentGuests""", """controllers.GuestController.getCurrentGuests"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """booking""", """controllers.BookingDetailsController.registerBookingDetails()"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """assets/""" + "$" + """file<.+>""", """controllers.Assets.versioned(path:String = "/public", file:Asset)"""),
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

  // @LINE:9
  private lazy val controllers_RoomController_create1_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("room")))
  )
  private lazy val controllers_RoomController_create1_invoker = createInvoker(
    RoomController_3.create(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.RoomController",
      "create",
      Nil,
      "POST",
      this.prefix + """room""",
      """""",
      Seq()
    )
  )

  // @LINE:11
  private lazy val controllers_GuestController_create2_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("guest")))
  )
  private lazy val controllers_GuestController_create2_invoker = createInvoker(
    GuestController_0.create(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.GuestController",
      "create",
      Nil,
      "POST",
      this.prefix + """guest""",
      """""",
      Seq()
    )
  )

  // @LINE:12
  private lazy val controllers_GuestController_getCurrentGuests3_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("currentGuests")))
  )
  private lazy val controllers_GuestController_getCurrentGuests3_invoker = createInvoker(
    GuestController_0.getCurrentGuests,
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.GuestController",
      "getCurrentGuests",
      Nil,
      "GET",
      this.prefix + """currentGuests""",
      """""",
      Seq()
    )
  )

  // @LINE:14
  private lazy val controllers_BookingDetailsController_registerBookingDetails4_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("booking")))
  )
  private lazy val controllers_BookingDetailsController_registerBookingDetails4_invoker = createInvoker(
    BookingDetailsController_2.registerBookingDetails(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.BookingDetailsController",
      "registerBookingDetails",
      Nil,
      "POST",
      this.prefix + """booking""",
      """""",
      Seq()
    )
  )

  // @LINE:17
  private lazy val controllers_Assets_versioned5_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("assets/"), DynamicPart("file", """.+""", encodeable=false)))
  )
  private lazy val controllers_Assets_versioned5_invoker = createInvoker(
    Assets_1.versioned(fakeValue[String], fakeValue[Asset]),
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


  def routes: PartialFunction[RequestHeader, Handler] = {
  
    // @LINE:7
    case controllers_HomeController_index0_route(params@_) =>
      call { 
        controllers_HomeController_index0_invoker.call(HomeController_4.index())
      }
  
    // @LINE:9
    case controllers_RoomController_create1_route(params@_) =>
      call { 
        controllers_RoomController_create1_invoker.call(RoomController_3.create())
      }
  
    // @LINE:11
    case controllers_GuestController_create2_route(params@_) =>
      call { 
        controllers_GuestController_create2_invoker.call(GuestController_0.create())
      }
  
    // @LINE:12
    case controllers_GuestController_getCurrentGuests3_route(params@_) =>
      call { 
        controllers_GuestController_getCurrentGuests3_invoker.call(GuestController_0.getCurrentGuests)
      }
  
    // @LINE:14
    case controllers_BookingDetailsController_registerBookingDetails4_route(params@_) =>
      call { 
        controllers_BookingDetailsController_registerBookingDetails4_invoker.call(BookingDetailsController_2.registerBookingDetails())
      }
  
    // @LINE:17
    case controllers_Assets_versioned5_route(params@_) =>
      call(Param[String]("path", Right("/public")), params.fromPath[Asset]("file", None)) { (path, file) =>
        controllers_Assets_versioned5_invoker.call(Assets_1.versioned(path, file))
      }
  }
}
