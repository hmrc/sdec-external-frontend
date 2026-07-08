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
import play.api.libs.json.*

class ThreadStatusSpec extends AnyWordSpec with Matchers {

  "ThreadStatus Format" should {

    "write Draft" in {
      Json.toJson(ThreadStatus.Draft) shouldBe JsString("Draft")
    }

    "write Active" in {
      Json.toJson(ThreadStatus.Active) shouldBe JsString("Active")
    }

    "write Closed" in {
      Json.toJson(ThreadStatus.Closed) shouldBe JsString("Closed")
    }

    "write Archived" in {
      Json.toJson(ThreadStatus.Archived) shouldBe JsString("Archived")
    }

    "read Draft" in {
      JsString("Draft").as[ThreadStatus] shouldBe ThreadStatus.Draft
    }

    "read Active" in {
      JsString("Active").as[ThreadStatus] shouldBe ThreadStatus.Active
    }

    "read Closed" in {
      JsString("Closed").as[ThreadStatus] shouldBe ThreadStatus.Closed
    }

    "read Archived" in {
      JsString("Archived").as[ThreadStatus] shouldBe ThreadStatus.Archived
    }

    "return a JsError for an unknown value" in {
      JsString("Invalid").validate[ThreadStatus] shouldBe
        JsError("Unknown ThreadStatus: Invalid")
    }

    "return a JsError when the JSON is not a string" in {
      JsNumber(1).validate[ThreadStatus] shouldBe
        JsError("Expected JSON string")
    }
  }
}
