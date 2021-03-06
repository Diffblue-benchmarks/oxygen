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
package vip.justlive.oxygen.core;

/**
 * 插件
 *
 * @author wubo
 */
public interface Plugin extends Order {

  /**
   * 程序启动
   */
  default void start() {
  }

  /**
   * 程序停止
   */
  default void stop() {
  }

  /**
   * 前置处理
   */
  default void beforeInvoke() {
  }

  /**
   * 后置处理
   */
  default void afterInvoke() {
  }

  /**
   * 异常处理
   */
  default void onExceptionInvoke() {
  }

  /**
   * 最终处理
   */
  default void finalInvoke() {
  }

}
