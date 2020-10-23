package eci.ieti.data;

import eci.ieti.data.model.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.domain.Pageable;
public interface TodoRepository extends CrudRepository<Todo,String> {

    Page<Todo> findByResponsibleContaining(String responsible, Pageable pageable);
}
