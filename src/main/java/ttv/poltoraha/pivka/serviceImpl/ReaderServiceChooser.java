package ttv.poltoraha.pivka.serviceImpl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ttv.poltoraha.pivka.entity.MyUser;
import ttv.poltoraha.pivka.repository.MyUserRepository;
import ttv.poltoraha.pivka.service.ReaderService;

@Service
@RequiredArgsConstructor
public class ReaderServiceChooser {

    private final ReaderServiceImpl readerServiceImpl;
    private final ReaderServiceForNewReaders readerServiceForNewReaders;
    private final MyUserRepository myUserRepository;

    public ReaderService getReaderService(String username) {
        MyUser user = myUserRepository.findById(username)
                .orElseThrow(() -> new EntityNotFoundException("User " + username + " not found!"));

        if (user.isNeedsPasswordReset()) {
            return readerServiceForNewReaders;
        } else {
            return readerServiceImpl;
        }
    }
}
