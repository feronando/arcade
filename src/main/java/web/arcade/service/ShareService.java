package web.arcade.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.arcade.repository.ProfileRepository;
import web.arcade.repository.ShareRepository;

@Service
public class ShareService {
    private ShareRepository shareRepository;

    @Autowired
    public ShareService(ShareRepository shareRepository) {
        this.shareRepository = shareRepository;
    }
}
