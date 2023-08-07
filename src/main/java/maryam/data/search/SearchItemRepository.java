package maryam.data.search;

import maryam.models.search.SearchItem;
import maryam.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SearchItemRepository extends JpaRepository<SearchItem,Long> {
    public SearchItem findByUser(User user);
    public Optional<SearchItem> findByTextAndUser(String text,User user);
}
