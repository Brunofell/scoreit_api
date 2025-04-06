package com.scoreit.scoreit.service;

import com.cloudinary.Cloudinary;
import java.util.Map;

import com.cloudinary.utils.ObjectUtils;
import com.scoreit.scoreit.entity.Member;
import com.scoreit.scoreit.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.Optional;

@Service
public class ImageService {
    @Autowired
    private Cloudinary cloudinary;
    @Autowired
    private MemberRepository memberRepository;

    public String uploadImage(Long id, MultipartFile file) throws IOException {
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
        String imageUrl = uploadResult.get("secure_url").toString();

        // Busca o usuário e atualiza
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        member.setProfileImageUrl(imageUrl);
        memberRepository.save(member);


        return imageUrl;
    }

}
