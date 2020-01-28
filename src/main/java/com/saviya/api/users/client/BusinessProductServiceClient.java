package com.saviya.api.users.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import feign.FeignException;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;

@FeignClient(name = "products-ws", fallbackFactory = BusinessProductServiceClientFallBackFactory.class)
public interface BusinessProductServiceClient {

	@GetMapping("v1/saviya/business")
	public String getBusinessProduct();

}

@Component
class BusinessProductServiceClientFallBackFactory implements FallbackFactory<BusinessProductServiceClient> {

	@Override
	public BusinessProductServiceClient create(Throwable cause) {
		return new BusinessProductServiceClientFallBack(cause);
	}

}

@Slf4j
class BusinessProductServiceClientFallBack implements BusinessProductServiceClient {

	private final Throwable cause;

	public BusinessProductServiceClientFallBack(Throwable cause) {
		this.cause = cause;
	}

	@Override
	public String getBusinessProduct() {

		if (cause instanceof FeignException && ((FeignException) cause).status() == HttpStatus.NOT_FOUND.ordinal()) {
			log.error("Feign exception while gettign data :-" + cause.getLocalizedMessage());
		} else {
			log.error("Other error while gettign data :-" + cause.getLocalizedMessage());
		}

		return null;
	}

}