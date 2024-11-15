// @GENERATOR:play-routes-compiler
// @SOURCE:conf/routes

package controllers;

import router.RoutesPrefix;

public class routes {
  
  public static final controllers.ReverseAssets Assets = new controllers.ReverseAssets(RoutesPrefix.byNamePrefix());
  public static final controllers.ReverseEventController EventController = new controllers.ReverseEventController(RoutesPrefix.byNamePrefix());
  public static final controllers.ReverseHomeController HomeController = new controllers.ReverseHomeController(RoutesPrefix.byNamePrefix());
  public static final controllers.ReverseIssueController IssueController = new controllers.ReverseIssueController(RoutesPrefix.byNamePrefix());
  public static final controllers.ReverseTeamController TeamController = new controllers.ReverseTeamController(RoutesPrefix.byNamePrefix());
  public static final controllers.ReverseTaskController TaskController = new controllers.ReverseTaskController(RoutesPrefix.byNamePrefix());

  public static class javascript {
    
    public static final controllers.javascript.ReverseAssets Assets = new controllers.javascript.ReverseAssets(RoutesPrefix.byNamePrefix());
    public static final controllers.javascript.ReverseEventController EventController = new controllers.javascript.ReverseEventController(RoutesPrefix.byNamePrefix());
    public static final controllers.javascript.ReverseHomeController HomeController = new controllers.javascript.ReverseHomeController(RoutesPrefix.byNamePrefix());
    public static final controllers.javascript.ReverseIssueController IssueController = new controllers.javascript.ReverseIssueController(RoutesPrefix.byNamePrefix());
    public static final controllers.javascript.ReverseTeamController TeamController = new controllers.javascript.ReverseTeamController(RoutesPrefix.byNamePrefix());
    public static final controllers.javascript.ReverseTaskController TaskController = new controllers.javascript.ReverseTaskController(RoutesPrefix.byNamePrefix());
  }

}
