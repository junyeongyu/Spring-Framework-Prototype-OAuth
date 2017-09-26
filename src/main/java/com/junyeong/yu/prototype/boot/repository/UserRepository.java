package com.junyeong.yu.prototype.boot.repository;

import com.junyeong.yu.prototype.boot.model.User;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.socialsignin.spring.data.dynamodb.repository.EnableScanCount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

@EnableScan
@EnableScanCount
public interface UserRepository extends PagingAndSortingRepository<User, String> {
    public Page<User> findByLastName(String lastName, Pageable pageable);

    //@EnableScan
    //@EnableScanCount
    public Page<User> findAll(Pageable pageable);
}