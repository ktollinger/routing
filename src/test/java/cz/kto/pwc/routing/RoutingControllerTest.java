package cz.kto.pwc.routing;

import cz.kto.pwc.routing.controller.RoutingController;
import cz.kto.pwc.routing.exception.RouteNotFoundException;
import cz.kto.pwc.routing.exception.RoutingException;
import cz.kto.pwc.routing.model.Route;
import cz.kto.pwc.routing.service.RoutingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RoutingControllerTest {

    private MockMvc mockMvc;

    @BeforeEach
    void setup() throws RoutingException {
        final RoutingService routingService = createRoutingServiceMock();
        final RoutingController routingController = new RoutingController(routingService);
        final StandaloneMockMvcBuilder builder = MockMvcBuilders.standaloneSetup(routingController);
        this.mockMvc = builder.build();
    }

    private RoutingService createRoutingServiceMock() throws RoutingException {
        final RoutingService routingService = Mockito.mock(RoutingService.class);
        Mockito.when(routingService.route("CZE", "THA")).thenReturn(new Route("CZE", "POL", "RUS", "CHN", "MMR", "THA"));
        Mockito.when(routingService.route("CZE", "AUT")).thenReturn(new Route("CZE", "AUT"));;
        Mockito.when(routingService.route("CZE", "CZE")).thenReturn(new Route("CZE"));
        Mockito.when(routingService.route("CZE", "ZOO")).thenThrow(RouteNotFoundException.class);
        return routingService;
    }

    @Test
    public void routing_CZE_AUT() throws Exception {
        final ResultActions actions = this.mockMvc
                .perform(get("/routing/CZE/AUT").accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andDo(result -> System.out.println(result.getResponse().getContentAsString()))
                .andExpect(jsonPath("$.route").value(is(List.of("CZE", "AUT"))));
    }

    @Test
    public void routing_CZE_CZE() throws Exception {
        final ResultActions actions = this.mockMvc
                .perform(get("/routing/CZE/CZE").accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andDo(result -> System.out.println(result.getResponse().getContentAsString()))
                .andExpect(jsonPath("$.route").value(is(List.of("CZE"))));
    }

    @Test
    public void routing_CZE_ZOO() throws Exception {
        final ResultActions actions = this.mockMvc
                .perform(get("/routing/CZE/ZOO").accept(APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andDo(result -> System.out.println(result.getResponse().getContentAsString()));
    }

}
