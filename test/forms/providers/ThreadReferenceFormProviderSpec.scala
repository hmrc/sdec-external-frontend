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

package forms.providers

import base.SpecBase
import forms.behaviours.FieldBehaviours
import play.api.data.FormError

class ThreadReferenceFormProviderSpec extends SpecBase with FieldBehaviours {

  private val form = new ThreadReferenceFormProvider()()

  "ThreadReferenceFormProvider" - {

    "bind a valid thread reference" in {

      val result = form.bind(
        Map("thread-reference" -> "ABC123DEF456")
      )

      result.errors mustBe empty
      result.value.value.reference mustBe "ABC123DEF456"
    }

    "require a value" in {

      val result = form.bind(
        Map("thread-reference" -> "")
      )

      result.errors must contain only
        FormError(
          "thread-reference",
          "sdec.landingpage.error.enterref"
        )
    }

    "reject lowercase characters" in {

      val result = form.bind(
        Map("thread-reference" -> "abc123DEF456")
      )

      result.errors must contain
      FormError(
        "thread-reference",
        "sdec.landingpage.error.threadref.help"
      )
    }

    "reject special characters" in {

      val result = form.bind(
        Map("thread-reference" -> "ABC123DEF45!")
      )

      result.errors must contain
      FormError(
        "thread-reference",
        "sdec.landingpage.error.threadref.help"
      )
    }

    "reject values that are too short" in {

      val result = form.bind(
        Map("thread-reference" -> "ABC123")
      )

      result.errors must contain
      FormError(
        "thread-reference",
        "sdec.landingpage.error.threadref.help"
      )
    }

    "reject values that are too long" in {

      val result = form.bind(
        Map("thread-reference" -> "ABC123DEF4567")
      )

      result.errors must contain
      FormError(
        "thread-reference",
        "sdec.landingpage.error.threadref.help"
      )
    }
  }
}
