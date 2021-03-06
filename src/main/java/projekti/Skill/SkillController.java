package projekti.Skill;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import projekti.Account.Account;
import projekti.Account.AccountRepository;
import projekti.Account.AccountService;

@Controller
public class SkillController {
    
    @Autowired
    private SkillRepository skillRepository;
    
    @Autowired
    private AccountRepository accountRepository;
    
    @Autowired
    private AccountService accountService;
    
    @GetMapping("/profile/{path}/skill")
    public String list(Model model, String path) {

        return "redirect:/profile/" + path;
    }
    
        
    @PostMapping("/profile/{path}/skill")
    public String addSkill(@PathVariable String path, @RequestParam String content) {
        Account a = accountRepository.findByPath(path);
        Skill skill = new Skill();
        skill.setName(content);
        skillRepository.save(skill);
        a.getSkills().add(skill);
        accountRepository.save(a);
        
        return "redirect:/profile/" + path;
    }
    
    @PostMapping("/profile/{path}/skill/{id}/like")
    public String skillLike(@PathVariable String path, @PathVariable Long id) {
        Account liker = accountService.getUser();
        Skill skill = skillRepository.getOne(id);
            
        if (skill.getLikes().contains(liker)) {
            skill.getLikes().remove(liker);
            skillRepository.save(skill);
            
            return "redirect:/profile/" + path;
        }
            
        skill.getLikes().add(liker);
        skillRepository.save(skill);
            
        return "redirect:/profile/" + path;
    }
    
}