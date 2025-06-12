package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.repository.ChannelRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class JCFChannelRepository implements ChannelRepository {

    public List<Channel> data = new ArrayList<>();

    public JCFChannelRepository() {
        data = new ArrayList<>();
    }

    @Override
    public void saveAll(List<Channel> channels) {
        data = channels;
    }

    @Override
    public List<Channel> findAll() {
        return data;
    }

    @Override
    public Optional<Channel> findById(UUID id) {
        return findAll().stream()
                .filter(c -> c.getId().equals(id))
                .findFirst();
    }

    @Override
    public void save(Channel channel) {

        // replace if same ID, add if none
        boolean updated = false;

        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getId().equals(channel.getId())) {
                data.set(i, channel);
                updated = true;
                break;
            }
        }

        if (!updated) {
            data.add(channel);
        }

        saveAll(data);
    }

    @Override
    public void deleteById(UUID id) {
        data.removeIf(c -> c.getId().equals(id));
    }
}
