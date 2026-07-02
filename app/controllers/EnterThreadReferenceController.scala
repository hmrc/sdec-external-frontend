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

import controllers.actions.IdentifierAction
import forms.models.ThreadReference
import forms.providers.ThreadReferenceFormProvider
import models.Mode
import play.api.data.Form
import play.api.i18n.{I18nSupport, Messages}
import play.api.mvc.*
import play.api.{Logger, Logging}
import uk.gov.hmrc.play.bootstrap.frontend.controller.FrontendBaseController
import views.html.{EnterThreadReferenceView, ThreadReferenceView}

import javax.inject.Inject

class EnterThreadReferenceController @Inject() (
    val controllerComponents: MessagesControllerComponents,
    identify: IdentifierAction,
    enterThreadReferenceView: EnterThreadReferenceView,
    formProvider: ThreadReferenceFormProvider,
    threadReferenceView: ThreadReferenceView
) extends FrontendBaseController
    with I18nSupport
    with Logging {
  private val logger              = Logger(getClass)
  val form: Form[ThreadReference] = ThreadReference.form

  def onPageLoad(
      mode: Mode,
      form: Form[ThreadReference] = form
  ): Action[AnyContent] = identify { implicit request =>
    val hasErrors = form.hasErrors
    logger.info(s"onPageLoad: HasErrors: $hasErrors, Loading page with $form")
    Ok(enterThreadReferenceView(form, mode))
  }

  def onContinue(mode: Mode): Action[AnyContent] = identify { implicit request =>
    val formData: Form[ThreadReference] = ThreadReference.form.bindFromRequest()
    val threadReference: Option[ThreadReference] = formData.value
    val hasErrors                                = formData.hasErrors
    logger.info(
      s"onContinue: HasErrors: $hasErrors, TR: $threadReference, FormData: $formData"
    )
    validateThreadReference(threadReference)
      .fold(returnBadRequest(formData, mode))(t => Ok(threadReferenceView(mode, t)))
  }

  private def returnBadRequest(form: Form[ThreadReference], mode: Mode)(using
      request: Request[?]
  ): Result = {
    val formWithError =
      form.withGlobalError(Messages("sdec.landingpage.error.problem.message"))
    logger.info(s"returnBadRequests: Form has errors: $formWithError")
    BadRequest(enterThreadReferenceView(formWithError, mode))
  }

  private def validateThreadReference(
      tr: Option[ThreadReference]
  ): Option[ThreadReference] = {
    tr match {
      case Some(value) =>
        if (formProvider.validateThreadReference(value.reference)) {
          tr
        } else {
          None
        }
      case None => None
    }
  }
}
