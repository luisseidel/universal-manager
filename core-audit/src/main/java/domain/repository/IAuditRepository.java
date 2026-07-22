package domain.repository;

import com.manager.shared.repository.IRepository;
import domain.model.AuditLog;

import java.util.List;

public interface IAuditRepository extends IRepository<AuditLog, String> {

    List<AuditLog> findByEventType(String type);

}
