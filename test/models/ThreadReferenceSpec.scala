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

package models

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import play.api.libs.json.Json

import java.time.{LocalDate, LocalDateTime}

class ThreadReferenceSpec extends AnyWordSpec with Matchers {

  "ThreadReference JSON format" should {

    "deserialize JSON" in {

      val json = Json.parse(
        """
        {
          "id":"1",
          "threadReference":"123456789012",
          "status":"Active",
          "createdTimeStamp":"2026-07-06T12:00:00",
          "lastUpdatedTimeStamp":"2026-07-06T13:00:00",
          "threadExpiryDate":"2026-08-06",
          "associatedCaseReference":"CASE123"
        }
        """
      )

      json.as[ThreadReference] shouldBe ThreadReference(
        id = "1",
        threadReference = "123456789012",
        status = ThreadStatus.Active,
        createdTimeStamp = LocalDateTime.of(2026, 7, 6, 12, 0),
        lastUpdatedTimeStamp = LocalDateTime.of(2026, 7, 6, 13, 0),
        threadExpiryDate = LocalDate.of(2026, 8, 6),
        associatedCaseReference = "CASE123"
      )
    }
  }
}
