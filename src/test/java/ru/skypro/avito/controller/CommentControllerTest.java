package ru.skypro.avito.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import ru.skypro.avito.dto.CommentDto;
import ru.skypro.avito.dto.Role;
import ru.skypro.avito.model.Ads;
import ru.skypro.avito.model.Comment;
import ru.skypro.avito.model.User;
import ru.skypro.avito.repository.AdsRepository;
import ru.skypro.avito.repository.CommentRepository;
import ru.skypro.avito.repository.UserRepository;
import ru.skypro.avito.security.UserDetailsServiceImpl;

import java.time.Instant;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AdsRepository adsRepository;
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    private Authentication auth;
    private final User user = new User();
    private final Ads ads = new Ads();
    private final Comment comment = new Comment();
    private final CommentDto commentDto = new CommentDto();

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

        ads.setTitle("Ads");
        ads.setDescription("description");
        ads.setPrice(1000);
        ads.setAuthor(user);
        adsRepository.save(ads);

        comment.setText("Text");
        comment.setAds(ads);
        comment.setCreatedAt(Instant.now());
        comment.setAuthor(user);
        commentRepository.save(comment);

        commentDto.setText("TEXT");
    }

    @AfterEach
    void cleatUp() {
        commentRepository.delete(comment);
        adsRepository.delete(ads);
        userRepository.delete(user);
    }

    @Test
    public void testGetCommentsReturnsCorrectCommentsList() throws Exception {
        mockMvc.perform(get("/ads/{id}/comments", ads.getId())
                        .with(authentication(auth)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.count").isNumber())
                .andExpect(jsonPath("$.results").isArray())
                .andExpect(jsonPath("$.results[0].text").value(comment.getText()));
    }

    @Test
    public void testDeleteAdsCommentReturnsOkWhenCommentRemoved() throws Exception {
        mockMvc.perform(delete("/ads/{adId}/comments/{commentId}", ads.getId(), comment.getId())
                        .with(authentication(auth)))
                .andExpect(status().isOk());
    }


}
