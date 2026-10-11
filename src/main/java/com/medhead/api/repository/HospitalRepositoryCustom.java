package com.medhead.api.repository;

import com.medhead.api.model.Hospital;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface HospitalRepositoryCustom {

    @Query(value = "SELECT h FROM Hospital h WHERE Freebed > 0")
    List<Hospital> findByFreebed();
    
    @Query(value = "SELECT h FROM Hospital h WHERE Spec LIKE %?1%")
    List<Hospital> findBySpecialities(String spec);
    
    @Query(value = "SELECT h FROM Hospital h WHERE Spec LIKE %?1% AND Freebed > 0")
    List<Hospital> findByFreebedAndBySpecialities(String spec);
    
}
