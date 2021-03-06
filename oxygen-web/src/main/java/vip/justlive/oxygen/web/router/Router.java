/*
 * Copyright (C) 2018 justlive1
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package vip.justlive.oxygen.web.router;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;
import lombok.experimental.UtilityClass;
import vip.justlive.oxygen.core.exception.Exceptions;
import vip.justlive.oxygen.web.http.HttpMethod;

/**
 * Router
 *
 * @author wubo
 */
@UtilityClass
public class Router {

  static final Pattern REGEX_PATH_GROUP = Pattern.compile("\\{(\\w+)[}]");
  private static final Map<String, Route> SIMPLE_HANDLERS = new ConcurrentHashMap<>(4, 1);
  private static final Map<String, Route> REGEX_HANDLERS = new ConcurrentHashMap<>(4, 1);
  private static final Map<String, RouteHandler> STATIC_HANDLERS = new HashMap<>(4, 1);
  private static final List<Route> ROUTES = new LinkedList<>();
  private static final List<StaticRoute> STATIC_ROUTES = new LinkedList<>();

  /**
   * 构建router
   *
   * @return route
   */
  public static Route router() {
    Route route = new Route();
    ROUTES.add(route);
    return route;
  }

  /**
   * 构建静态资源route
   *
   * @return route
   */
  public static StaticRoute staticRoute() {
    StaticRoute route = new StaticRoute();
    STATIC_ROUTES.add(route);
    return route;
  }

  /**
   * build
   */
  public static void build() {
    ROUTES.forEach(Router::buildRoute);
    STATIC_ROUTES.forEach(Router::buildStaticRoute);
  }

  /**
   * lookup route
   *
   * @param method request method
   * @param path request path
   * @return route
   */
  public static Route lookup(HttpMethod method, String path) {
    Route route = SIMPLE_HANDLERS.get(path);
    if (route != null && route.methods().contains(method)) {
      return route;
    }
    for (Map.Entry<String, Route> entry : REGEX_HANDLERS.entrySet()) {
      if (Pattern.compile(entry.getKey()).matcher(path).matches() && entry.getValue().methods()
          .contains(method)) {
        return entry.getValue();
      }
    }
    return null;
  }

  /**
   * lookup static handle
   *
   * @param path path
   * @return handler
   */
  public static RouteHandler lookupStatic(String path) {
    for (Map.Entry<String, RouteHandler> entry : STATIC_HANDLERS.entrySet()) {
      if (path.startsWith(entry.getKey())) {
        return entry.getValue();
      }
    }
    return null;
  }

  /**
   * clear
   */
  public static void clear() {
    SIMPLE_HANDLERS.clear();
    REGEX_HANDLERS.clear();
  }

  private static void buildRoute(Route route) {
    if (route.methods().isEmpty()) {
      route.methods().addAll(Arrays.asList(HttpMethod.values()));
    }
    Route exist;
    if (route.regex()) {
      exist = REGEX_HANDLERS.putIfAbsent(route.path(), route);
    } else {
      exist = SIMPLE_HANDLERS.putIfAbsent(route.path(), route);
    }
    if (exist != null) {
      throw Exceptions.fail(String.format("path [%s] already exists", route.path()));
    }
  }

  private static void buildStaticRoute(StaticRoute route) {
    if (STATIC_HANDLERS.putIfAbsent(route.prefix(), new StaticRouteHandler(route)) != null) {
      throw Exceptions.fail(String.format("path [%s] already exists", route.prefix()));
    }
  }
}
