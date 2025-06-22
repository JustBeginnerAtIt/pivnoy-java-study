package ttv.poltoraha.pivka.service;

import ttv.poltoraha.pivka.entity.MyUser;
import ttv.poltoraha.pivka.entity.Quote;

public interface QuoteRatingService {
    void rateQuote(MyUser user, Quote quote, Integer rating);
}
