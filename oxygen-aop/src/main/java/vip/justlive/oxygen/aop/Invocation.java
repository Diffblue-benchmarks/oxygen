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
package vip.justlive.oxygen.aop;

import java.lang.reflect.Method;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * aop调用封装
 *
 * @author wubo
 */
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public final class Invocation {

  @NonNull
  private Object target;
  @NonNull
  private Method method;
  @NonNull
  private Object[] args;
  private Object returnValue;

}
