package com.security.wiremock;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ClassPathResource;

import com.github.tomakehurst.wiremock.client.WireMock;

public class MockServer {

	public static void main(String[] args) throws IOException {

		WireMock.configureFor(8062);
		WireMock.removeAllMappings();

		mock("/order/1", "mock/response/01.txt");
	}

	private static void mock(String url, String fileName) throws IOException {
		ClassPathResource resource = new ClassPathResource(fileName);
		String content = FileUtils.readFileToString(resource.getFile(), "UTF-8");
		WireMock.stubFor(get(urlEqualTo(url)).willReturn(aResponse().withBody(content).withStatus(200)));
	}

}
