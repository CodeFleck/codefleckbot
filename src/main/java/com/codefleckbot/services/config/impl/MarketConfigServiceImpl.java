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

import com.codefleckbot.domain.market.MarketConfig;
import com.codefleckbot.repository.MarketConfigRepository;
import com.codefleckbot.services.config.MarketConfigService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementation of the Market config service.
 *
 * @author gazbert
 */
@Service("marketConfigService")
@Transactional
@ComponentScan(basePackages = {"com.codefleck.repository"})
public class MarketConfigServiceImpl implements MarketConfigService {

  private static final Logger LOG = LogManager.getLogger();
  private final MarketConfigRepository marketConfigRepository;

  @Autowired
  public MarketConfigServiceImpl(@Qualifier("marketConfigYamlRepository")
      MarketConfigRepository marketConfigRepository) {
    this.marketConfigRepository = marketConfigRepository;
  }

  @Override
  public List<MarketConfig> getAllMarketConfig() {
    return marketConfigRepository.findAll();
  }

  @Override
  public MarketConfig getMarketConfig(String id) {
    LOG.info(() -> "Fetching Market config for id: " + id);
    return marketConfigRepository.findById(id);
  }

  @Override
  public MarketConfig updateMarketConfig(MarketConfig config) {
    LOG.info(() -> "About to update Market config: " + config);
    return marketConfigRepository.save(config);
  }

  @Override
  public MarketConfig createMarketConfig(MarketConfig config) {
    LOG.info(() -> "About to create Market config: " + config);
    return marketConfigRepository.save(config);
  }

  @Override
  public MarketConfig deleteMarketConfig(String id) {
    LOG.info(() -> "About to delete Market config for id: " + id);
    return marketConfigRepository.delete(id);
  }
}
