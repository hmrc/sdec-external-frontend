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

package controllers

import base.SpecBase
import config.FrontendAppConfig
import controllers.actions.IdentifierAction
import models.requests.IdentifierRequest
import org.scalatestplus.mockito.MockitoSugar.mock
import play.api.http.Status.SEE_OTHER
import play.api.mvc.*
import play.api.test.*
import play.api.test.Helpers.*
import uk.gov.hmrc.auth.core.AuthConnector

import scala.concurrent.{ExecutionContext, Future}

class IndexControllerSpec extends SpecBase {

  val identity: IdentifierAction = new IdentifierAction {
    override def parser: BodyParser[AnyContent] = Helpers.stubBodyParser()

    override def invokeBlock[A](
        request: Request[A],
        block: IdentifierRequest[A] => Future[Result]
    ): Future[Result] =
      block(IdentifierRequest(request, "test-user"))

    override protected def executionContext: ExecutionContext =
      scala.concurrent.ExecutionContext.global
  }

  val authConnector: AuthConnector = mock[AuthConnector]

  given feAppConfig: FrontendAppConfig = mock[FrontendAppConfig]
  given ExecutionContext               = scala.concurrent.ExecutionContext.global

  val ctr = new IndexController(
    Helpers.stubMessagesControllerComponents(),
    authConnector,
    identity
  )

  "Index Controller" - {
    "redirect to the enter thread reference page" in {
      val request = FakeRequest(GET, "/sdec")
      val result  = ctr.onPageLoad(request)

      status(result) mustBe SEE_OTHER
      redirectLocation(result) mustBe Some(
        routes.EnterThreadReferenceController.onPageLoad().url
      )
    }
  }
}
