/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2019 gazbert
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

package com.codefleckbot.repository.yaml;

import com.codefleckbot.datastore.ConfigurationManager;
import com.codefleckbot.datastore.emailalerts.EmailAlertsType;
import com.codefleckbot.domain.emailalerts.EmailAlertsConfig;
import com.codefleckbot.repository.EmailAlertsConfigRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import static com.codefleckbot.datastore.FileLocations.EMAIL_ALERTS_CONFIG_YAML_FILENAME;

/**
 * An Email Alerts config repo that uses a YAML backed datastore.
 *
 * @author gazbert
 */
@Repository("emailAlertsConfigYamlRepository")
@Transactional
public class EmailAlertsConfigYamlRepository implements EmailAlertsConfigRepository {

  private static final Logger LOG = LogManager.getLogger();

  @Override
  public EmailAlertsConfig get() {
    LOG.info(() -> "Fetching EmailAlertsConfig...");
    return ConfigurationManager.loadConfig(EmailAlertsType.class, EMAIL_ALERTS_CONFIG_YAML_FILENAME)
        .getEmailAlerts();
  }

  @Override
  public EmailAlertsConfig save(EmailAlertsConfig config) {
    LOG.info(() -> "About to save EmailAlertsConfig: " + config);

    final EmailAlertsType emailAlertsType = new EmailAlertsType();
    emailAlertsType.setEmailAlerts(config);
    ConfigurationManager.saveConfig(
        EmailAlertsType.class, emailAlertsType, EMAIL_ALERTS_CONFIG_YAML_FILENAME);

    return ConfigurationManager.loadConfig(EmailAlertsType.class, EMAIL_ALERTS_CONFIG_YAML_FILENAME)
        .getEmailAlerts();
  }
}
