package cz.kto.pwc.routing;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author kamil.tollinger@gmail.com Kamil Tollinger
 */
@SpringBootTest
@WebAppConfiguration
@ContextConfiguration(classes = RoutingApplication.class)
@AutoConfigureMockMvc
public class RoutingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void routing_CZE_THA() throws Exception {
        final ResultActions actions = this.mockMvc
                .perform(get("/routing/CZE/THA").accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andDo(result -> System.out.println(result.getResponse().getContentAsString()))
                .andExpect(jsonPath("$.route").value(is(List.of("CZE","POL","RUS","CHN","MMR","THA"))));
    }

}
