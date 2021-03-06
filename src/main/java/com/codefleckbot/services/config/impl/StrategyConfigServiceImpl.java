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

package com.codefleckbot.services.config.impl;

import com.codefleckbot.domain.strategy.StrategyConfig;
import com.codefleckbot.repository.StrategyConfigRepository;
import com.codefleckbot.services.config.StrategyConfigService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementation of the Strategy config service.
 *
 * @author gazbert
 */
@Service("strategyConfigService")
@Transactional
@ComponentScan(basePackages = {"com.codefleck.repository"})
public class StrategyConfigServiceImpl implements StrategyConfigService {

  private static final Logger LOG = LogManager.getLogger();
  private final StrategyConfigRepository strategyConfigRepository;

  @Autowired
  public StrategyConfigServiceImpl(@Qualifier("strategyConfigYamlRepository")
                                         StrategyConfigRepository strategyConfigRepository) {
    this.strategyConfigRepository = strategyConfigRepository;
  }

  @Override
  public List<StrategyConfig> getAllStrategyConfig() {
    return strategyConfigRepository.findAll();
  }

  @Override
  public StrategyConfig getStrategyConfig(String id) {
    LOG.info(() -> "Fetching Strategy config for id: " + id);
    return strategyConfigRepository.findById(id);
  }

  @Override
  public StrategyConfig updateStrategyConfig(StrategyConfig config) {
    LOG.info(() -> "About to update Strategy config: " + config);
    return strategyConfigRepository.save(config);
  }

  @Override
  public StrategyConfig createStrategyConfig(StrategyConfig config) {
    LOG.info(() -> "About to create Strategy config: " + config);
    return strategyConfigRepository.save(config);
  }

  @Override
  public StrategyConfig deleteStrategyConfig(String id) {
    LOG.info(() -> "About to delete Strategy config for id: " + id);
    return strategyConfigRepository.delete(id);
  }
}