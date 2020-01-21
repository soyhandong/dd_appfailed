package kr.co.ddapp.web.news;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 뉴스
 */
@Controller
@SuppressWarnings("unused")
public class NewsController {
    private static Logger logger = LoggerFactory.getLogger(NewsController.class);
    /**
     * 뉴스 홈
     */
    @RequestMapping(value="/news", method = RequestMethod.GET)
    public ModelAndView main(HttpServletRequest request){
        ModelAndView mav = new ModelAndView();
        mav.addObject("aa", "dd");
        mav.setViewName("index");
        return mav;
    }
}
