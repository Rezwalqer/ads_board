package ru.skypro.avito.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import ru.skypro.avito.dto.CreateAds;
import ru.skypro.avito.dto.Role;
import ru.skypro.avito.model.Ads;
import ru.skypro.avito.model.Image;
import ru.skypro.avito.model.User;
import ru.skypro.avito.repository.AdsRepository;
import ru.skypro.avito.repository.UserRepository;
import ru.skypro.avito.security.UserDetailsServiceImpl;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class AdsControllerTest {

    private final User user = new User();
    private final CreateAds createAds = new CreateAds();
    private final Ads ads = new Ads();
    private final Image image = new Image();
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private AdsRepository adsRepository;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    private Authentication auth;

    @BeforeEach
    void setUp() {
        user.setEmail("username@mail.ru");
        user.setFirstName("User");
        user.setLastName("Test");
        user.setPhone("+79609279284");
        user.setPassword(encoder.encode("password"));
        user.setRole(Role.USER);
//        user.setEnabled(true);
        userRepository.save(user);

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        auth = new UsernamePasswordAuthenticationToken(userDetails.getUsername(),
                userDetails.getPassword(),
                userDetails.getAuthorities());

        createAds.setTitle("Ads");
        createAds.setDescription("description");
        createAds.setPrice(1000);

        ads.setTitle("Ads");
        ads.setDescription("description");
        ads.setPrice(1000);
        ads.setAuthor(user);
        adsRepository.save(ads);
    }

    @AfterEach
    void cleanUp() {
        userRepository.delete(user);
    }

    @Test
    public void testGetAllAdsReturnsCorrectAdsList() throws Exception {
        mockMvc.perform(get("/ads"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.count").isNumber())
                .andExpect(jsonPath("$.results").isArray());
    }

    @Test
    public void testGetFullAddReturnsCorrectAds() throws Exception {
        mockMvc.perform(get("/ads/{id}", ads.getId())
                        .with(authentication(auth)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pk").value(ads.getId()))
                .andExpect(jsonPath("$.title").value(ads.getTitle()))
                .andExpect(jsonPath("$.description").value(ads.getDescription()))
                .andExpect(jsonPath("$.price").value(ads.getPrice()))
                .andExpect(jsonPath("$.email").value(user.getEmail()))
                .andExpect(jsonPath("$.authorFirstName").value(user.getFirstName()))
                .andExpect(jsonPath("$.authorLastName").value(user.getLastName()))
                .andExpect(jsonPath("$.phone").value(user.getPhone()));
    }

    @Test
    public void testUpdateAdsReturnsUpdatedAds() throws Exception {
        String newTitle = "New Ads";
        String newDesc = "New Description";
        Integer newPrice = 2000;
        ads.setTitle(newTitle);
        ads.setDescription(newDesc);
        ads.setPrice(newPrice);
        adsRepository.save(ads);

        mockMvc.perform(patch("/ads/{id}", ads.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ads))
                        .with((authentication(auth))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(newTitle))
                .andExpect(jsonPath("$.description").value(newDesc))
                .andExpect(jsonPath("$.price").value(newPrice));
    }

}
