package maryam.data.visit;

import maryam.models.user.User;
import maryam.models.uservisit.Visit;

import java.util.List;

public interface CustomVisitRepository {
    List<Visit> getVisitsGroupByUserAndOrderByCreateAt(User user);
}
