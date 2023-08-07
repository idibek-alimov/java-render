package maryam.service.search;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import maryam.data.search.SearchItemRepository;
import maryam.models.search.SearchItem;
import maryam.service.user.UserService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class SearchItemService {
    private final SearchItemRepository searchItemRepository;
    private final UserService userService;
    private SearchItem createSearchItem(String searchText){
        try {
            SearchItem searchItem = new SearchItem().builder().user(userService.getCurrentUser()).text(searchText).count(1).build();
            return searchItemRepository.save(searchItem);
        }
        catch (Exception e){
            return null;
        }
    }
    public SearchItem addSearchItem(String searchText){
       Optional<SearchItem> optionalSearchItem = searchItemRepository.findByTextAndUser(searchText,userService.getCurrentUser());
       try {
           if (optionalSearchItem.isPresent()) {
               SearchItem searchItem = optionalSearchItem.get();
               searchItem = searchItem.updateSearchItem();
               return searchItem;
           } else {
               return createSearchItem(searchText);
           }
       }
       catch (Exception e){
           return null;
       }
    }

}
