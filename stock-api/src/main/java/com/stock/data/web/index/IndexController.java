package com.stock.data.web.index;

import com.stock.data.config.auth.LoginUser;
import com.stock.data.config.auth.dto.SessionUser;
import com.stock.data.web.posts.PostsService;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class IndexController {

	private final PostsService postsService;
	private final HttpSession httpSession;

	@GetMapping("/")
	public String index(Model model, @LoginUser SessionUser user){
//		SessionUser user = (SessionUser) httpSession.getAttribute("user");

		if(user != null){
			model.addAttribute("userName", user.getName());
		}

		return "index";
	}
}
