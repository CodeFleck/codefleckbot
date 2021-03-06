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

package com.codefleckbot.restcontroller.v1.runtime;

import com.codefleckbot.domain.bot.BotStatus;
import com.codefleckbot.domain.engine.EngineConfig;
import com.codefleckbot.services.config.EngineConfigService;
import com.codefleckbot.services.runtime.BotStatusService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Date;

import static com.codefleckbot.rest.EndpointLocations.RUNTIME_ENDPOINT_BASE_URI;

/**
 * Controller for directing Bot Status requests.
 *
 * @author gazbert
 * @since 1.0
 */
@RestController
@RequestMapping(RUNTIME_ENDPOINT_BASE_URI)
public class BotStatusController {

  private static final Logger LOG = LogManager.getLogger();
  private static final String STATUS_RESOURCE_PATH = "/status";

  private final BotStatusService botStatusService;
  private final EngineConfigService engineConfigService;

  @Autowired
  public BotStatusController(
      BotStatusService botStatusService, EngineConfigService engineConfigService) {
    this.botStatusService = botStatusService;
    this.engineConfigService = engineConfigService;
  }

  /**
   * Returns the process status for the bot.
   *
   * @param principal the authenticated user making the request.
   * @return the process status.
   */
  @GetMapping(value = STATUS_RESOURCE_PATH)
  public BotStatus getStatus(Principal principal) {

    LOG.info(
        () -> "GET " + STATUS_RESOURCE_PATH + " - getStatus() - caller: " + principal.getName());

    final EngineConfig engineConfig = engineConfigService.getEngineConfig();
    final String status = botStatusService.getStatus();

    final BotStatus botStatus = new BotStatus();
    botStatus.setBotId(engineConfig.getBotId());
    botStatus.setDisplayName(engineConfig.getBotName());
    botStatus.setStatus(status);
    botStatus.setDatetime(new Date());

    LOG.info(() -> "Response: " + botStatus);
    return botStatus;
  }
}
