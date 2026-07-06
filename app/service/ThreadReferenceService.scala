/*
 * Copyright 2026 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package service

import config.FrontendAppConfig
import models.ThreadReference
import uk.gov.hmrc.http.HttpReads.Implicits.readFromJson
import uk.gov.hmrc.http.client.HttpClientV2
import uk.gov.hmrc.http.{HeaderCarrier, StringContextOps}

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class ThreadReferenceService @Inject (
    appConfig: FrontendAppConfig,
    http: HttpClientV2
)(using hc: HeaderCarrier, ec: ExecutionContext)
    extends ThreadReferenceServiceAlgebra {

  override def checkThreadReference(
      threadReference: String
  ): Future[ThreadReference] = {
    val url = s"${ThreadInformationAPI.GetThreadReference.url}$threadReference"
    http.get(url"$url").execute[ThreadReference]
  }

  private enum ThreadInformationAPI(val url: String) {
    case GetThreadReference
        extends ThreadInformationAPI(
          s"${appConfig.threadInformationApi}"
        )
  }
}
