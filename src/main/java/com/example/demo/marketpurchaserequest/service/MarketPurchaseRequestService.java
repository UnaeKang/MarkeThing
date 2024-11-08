package com.example.demo.marketpurchaserequest.service;

import com.example.demo.common.filter.dto.marketpurchaserequest.KeywordDto;
import com.example.demo.common.filter.dto.marketpurchaserequest.MarketPurchaseRequestFilterDto;
import com.example.demo.marketpurchaserequest.dto.DetailMarketPurchaseRequestDto;
import com.example.demo.marketpurchaserequest.dto.MarketPurchaseRequestDto;
import com.example.demo.marketpurchaserequest.dto.MarketPurchaseRequestPreviewDto;
import com.example.demo.marketpurchaserequest.entity.MarketPurchaseRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public interface MarketPurchaseRequestService {

    MarketPurchaseRequest createMarketPurchaseRequest(
            MarketPurchaseRequestDto marketPurchaseRequestDto);

    void deleteMarketPurchaseRequest(Long id);

    DetailMarketPurchaseRequestDto getMarketPurchaseRequest(Long requestId);

    Sort confirmSortOrder(String sort);

    Page<MarketPurchaseRequestPreviewDto> getRequestsByKeyword(KeywordDto keywordDto, Pageable pageable);

    Page<MarketPurchaseRequestPreviewDto> getRequestsWithinDistance(String email, double distance, Pageable pageable);

    Page<MarketPurchaseRequestPreviewDto> getRequestsByFilter(
            MarketPurchaseRequestFilterDto filterRequestDto, Pageable pageable);
}
