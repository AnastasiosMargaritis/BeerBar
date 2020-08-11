package com.beer.web.controller;

import com.beer.web.model.BeerDto;
import com.beer.web.model.BeerStyleEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.UUID;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(BeerController.class)
@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs
@ComponentScan(basePackages = "com.beer.web.mappers")
class BeerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void getBeerById() throws Exception {

        mockMvc.perform(get("/api/v1/beer/{beerId}", UUID.randomUUID().toString())
                .param("isCold", "yes")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("v1/beer",
                        pathParameters(
                            parameterWithName("beerId").description("UUID of desired beer to get.")
                        ), requestParameters(
                                parameterWithName("isCold").description("Is Beer Cold Query Param.")
                        ),
                        responseFields(
                                fieldWithPath("id").description("Id of Beer."),
                                fieldWithPath("version").description("Version number."),
                                fieldWithPath("createdDate").description("Date Created."),
                                fieldWithPath("lastModifiedDate").description("Date Updated."),
                                fieldWithPath("beerName").description("Beer Name."),
                                fieldWithPath("beerStyle").description("Beer Style."),
                                fieldWithPath("upc").description("UPC of Beer."),
                                fieldWithPath("price").description("Price of the beer."),
                                fieldWithPath("quantityOnHand").ignored()
                        )));
    }

    @Test
    void saveNewBeer() throws Exception {

        BeerDto beerDto = this.getValidBeerDto();
        String beerDtoJson = objectMapper.writeValueAsString(beerDto);

        ConstrainedFields field = new ConstrainedFields(BeerDto.class);

        mockMvc.perform(post("/api/v1/beer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(beerDtoJson))
                        .andExpect(status().isCreated())
                        .andDo(document("v1/beer",
                                requestFields(
                                        field.withPath("id").ignored(),
                                        field.withPath("version").ignored(),
                                        field.withPath("createdDate").ignored(),
                                        field.withPath("lastModifiedDate").ignored(),
                                        field.withPath("beerName").description("Name of the beer."),
                                        field.withPath("beerStyle").description("Style of the beer."),
                                        field.withPath("upc").description("UPC of Beer").attributes(),
                                        field.withPath("price").description("Price"),
                                        field.withPath("quantityOnHand").description("Quantity On hand")
                                )));

    }

    @Test
    void updateBeerById() throws Exception{

        BeerDto beerDto = this.getValidBeerDto();
        String beerDtoJson = objectMapper.writeValueAsString(beerDto);

        mockMvc.perform(put("/api/v1/beer/" + UUID.randomUUID().toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(beerDtoJson))
                .andExpect(status().isNoContent());
    }

    BeerDto getValidBeerDto(){
        return BeerDto.builder()
                .beerName("Nice Ale")
                .beerStyle(BeerStyleEnum.ALE)
                .price(new BigDecimal("9.99"))
                .upc(123123123123L)
                .build();

    }

    private static class ConstrainedFields {

        private final ConstraintDescriptions constraintDescriptions;

        ConstrainedFields(Class<?> input) {
            this.constraintDescriptions = new ConstraintDescriptions(input);
        }

        private FieldDescriptor withPath(String path) {
            return fieldWithPath(path).attributes(key("constraints").value(StringUtils
                    .collectionToDelimitedString(this.constraintDescriptions
                            .descriptionsForProperty(path), ". ")));
        }
    }
}