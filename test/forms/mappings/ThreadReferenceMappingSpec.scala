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

package forms.mappings

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ThreadReferenceMappingSpec extends AnyFlatSpec with Matchers {
  private val sut = new ThreadReferenceMapping {}

  it should "return false for empty string" in {
    assert(sut.validateThreadReference("") === false)
  }

  it should "return false for any string less than 12 characters" in {
    assert(sut.validateThreadReference("ABC123") === false)
  }

  it should "return false for any string more than 12 characters" in {
    assert(sut.validateThreadReference("ABC1235678901") === false)
  }

  it should "return false for any string with 12 characters but containing lower case" in {
    assert(sut.validateThreadReference("aBC123456789") === false)
  }

  it should "return false for any string with 12 characters but containing unsupported characters" in {
    assert(sut.validateThreadReference("=BC123456789") === false)
  }

  it should "return true for any string with 12 characters" in {
    assert(sut.validateThreadReference("ABC123456789") === true)
  }
}
