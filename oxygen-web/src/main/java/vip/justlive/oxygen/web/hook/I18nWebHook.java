/*
 *  Copyright (C) 2019 justlive1
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License
 *  is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 *  or implied. See the License for the specific language governing permissions and limitations under
 *  the License.
 */
package vip.justlive.oxygen.web.hook;

import java.util.Locale;
import lombok.extern.slf4j.Slf4j;
import vip.justlive.oxygen.core.config.ConfigFactory;
import vip.justlive.oxygen.core.config.CoreConf;
import vip.justlive.oxygen.core.constant.Constants;
import vip.justlive.oxygen.core.i18n.Lang;
import vip.justlive.oxygen.web.http.Request;
import vip.justlive.oxygen.web.router.RoutingContext;

/**
 * i18n
 *
 * @author wubo
 */
@Slf4j
public class I18nWebHook implements WebHook {

  @Override
  public boolean before(RoutingContext ctx) {
    Request request = ctx.request();
    CoreConf conf = ConfigFactory.load(CoreConf.class);
    String localeStr = request.getParam(conf.getI18nParamKey());
    if (localeStr == null || localeStr.length() == 0) {
      Locale locale = (Locale) request.getSession().get(conf.getI18nSessionKey());
      if (locale != null) {
        Lang.setThreadLocale(locale);
      }
    } else {
      String[] arr = localeStr.split(Constants.UNDERSCORE);
      if (arr.length != 2) {
        log.warn("locale [{}] is incorrect", localeStr);
      } else {
        Locale locale = new Locale(arr[0], arr[1]);
        if (log.isDebugEnabled()) {
          log.debug("change locale from [{}] to [{}]", Lang.currentThreadLocale(), locale);
        }
        request.getSession().put(conf.getI18nSessionKey(), locale);
        Lang.setThreadLocale(locale);
      }
    }
    return true;
  }

  @Override
  public void finished(RoutingContext ctx) {
    Lang.clearThreadLocale();
  }
}
