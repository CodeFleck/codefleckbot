/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 Gareth Jon Lynch
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.codefleckbot.restcontroller.v1.config;

import com.codefleckbot.domain.emailalerts.EmailAlertsConfig;
import com.codefleckbot.services.config.EmailAlertsConfigService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

import static com.codefleckbot.rest.EndpointLocations.CONFIG_ENDPOINT_BASE_URI;

/**
 * Controller for directing Email Alerts config requests.
 *
 * <p>Email Alerts config can only be fetched and updated - it cannot be deleted or created.
 *
 * <p>There is only 1 Email Alerter per bot.
 *
 * @author gazbert
 * @since 1.0
 */
@RestController
@RequestMapping(CONFIG_ENDPOINT_BASE_URI)
public class EmailAlertsConfigController {

  private static final Logger LOG = LogManager.getLogger();
  private static final String EMAIL_ALERTS_RESOURCE_PATH = "/email-alerts";
  private final EmailAlertsConfigService emailAlertsConfigService;

  @Autowired
  public EmailAlertsConfigController(EmailAlertsConfigService emailAlertsConfigService) {
    this.emailAlertsConfigService = emailAlertsConfigService;
  }

  /**
   * Returns the Email Alerts configuration for the bot.
   *
   * @param principal the authenticated user making the request.
   * @return the Email Alerts configuration.
   */
  @GetMapping(value = EMAIL_ALERTS_RESOURCE_PATH)
  public EmailAlertsConfig getEmailAlerts(Principal principal) {

    LOG.info(
        () ->
            "GET "
                + EMAIL_ALERTS_RESOURCE_PATH
                + " - getEmailAlerts() - caller: "
                + principal.getName());

    final EmailAlertsConfig emailAlertsConfig = emailAlertsConfigService.getEmailAlertsConfig();
    LOG.info(() -> "Response: " + emailAlertsConfig);
    return emailAlertsConfig;
  }

  /**
   * Updates the Email Alerts configuration for the bot.
   *
   * @param principal the authenticated user making the request.
   * @param config the Email Alerts config to update.
   * @return 200 'OK' HTTP status code and Email Alerts config in response body if update
   *     successful, some other HTTP status code otherwise.
   */
  @PutMapping(value = EMAIL_ALERTS_RESOURCE_PATH)
  public ResponseEntity<EmailAlertsConfig> updateEmailAlerts(
          Principal principal, @RequestBody EmailAlertsConfig config) {

    LOG.info(
        () ->
            "PUT "
                + EMAIL_ALERTS_RESOURCE_PATH
                + " - updateEmailAlerts() - caller: "
                + principal.getName());

    LOG.info(() -> "Request: " + config);

    final EmailAlertsConfig updatedConfig =
        emailAlertsConfigService.updateEmailAlertsConfig(config);
    return buildResponseEntity(updatedConfig);
  }

  private ResponseEntity<EmailAlertsConfig> buildResponseEntity(EmailAlertsConfig entity) {
    LOG.info(() -> "Response: " + entity);
    return new ResponseEntity<>(entity, null, HttpStatus.OK);
  }
}
