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
import models.{ThreadReference, ThreadStatus}
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.when
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.mockito.MockitoSugar
import uk.gov.hmrc.http.HeaderCarrier
import uk.gov.hmrc.http.client.{HttpClientV2, RequestBuilder}

import java.time.{LocalDate, LocalDateTime}
import scala.concurrent.{ExecutionContext, Future}

class ThreadReferenceServiceSpec
    extends AnyWordSpec
    with Matchers
    with MockitoSugar
    with ScalaFutures {

  given HeaderCarrier    = HeaderCarrier()
  given ExecutionContext = ExecutionContext.global

  "checkThreadReference" should {

    "return the ThreadReference returned by the API" in {

      val appConfig = mock[FrontendAppConfig]
      val http      = mock[HttpClientV2]
      val request   = mock[RequestBuilder]

      when(appConfig.threadInformationApi)
        .thenReturn("http://localhost:4001/sdec-threadinfo-api/thread-reference/")

      val expected = ThreadReference(
        id = "507f1f77bcf86cd799439011",
        threadReference = "123456789012",
        status = ThreadStatus.Active,
        createdTimeStamp = LocalDateTime.of(2026, 7, 6, 12, 0),
        lastUpdatedTimeStamp = LocalDateTime.of(2026, 7, 6, 13, 0),
        threadExpiryDate = LocalDate.of(2026, 8, 6),
        associatedCaseReference = "CASE-12345"
      )

      when(http.get(any())(using any()))
        .thenReturn(request)

      when(request.execute[ThreadReference](using any(), any()))
        .thenReturn(Future.successful(expected))

      val service = new ThreadReferenceService(appConfig, http)

      val result = service.checkThreadReference("123456789012")

      whenReady(result) { tr =>
        tr shouldBe expected
      }
    }
  }
}
