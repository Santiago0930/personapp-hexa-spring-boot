package co.edu.javeriana.as.personapp.mariadb.adapter;

import java.util.List;

import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import co.edu.javeriana.as.personapp.application.port.out.PhoneOutputPort;
import co.edu.javeriana.as.personapp.common.annotations.Adapter;
import co.edu.javeriana.as.personapp.domain.Phone;
import co.edu.javeriana.as.personapp.mariadb.entity.TelefonoEntity;
import co.edu.javeriana.as.personapp.mariadb.mapper.TelefonoMapperMaria;
import co.edu.javeriana.as.personapp.mariadb.repository.PhoneRepositoryMaria;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Adapter("phoneOutputAdapterMaria")
@Transactional
public class PhoneOutputAdapterMaria implements PhoneOutputPort {

	@Autowired
	private PhoneRepositoryMaria phoneRepositoryMaria;

	@Autowired
	private TelefonoMapperMaria phoneMapperMaria;

	@Override
	public Phone save(Phone phone) {
		log.debug("Into save on Adapter MariaDB");
        TelefonoEntity persistedPhone = phoneRepositoryMaria.save(phoneMapperMaria.fromDomainToAdapter(phone));
		return phoneMapperMaria.fromAdapterToDomain(persistedPhone);
	}

	@Override
	public Boolean delete(String identification) {
		log.debug("Into delete on Adapter MariaDB");
        phoneRepositoryMaria.deleteById(identification);
		return phoneRepositoryMaria.findById(identification).isEmpty();
	}

	@Override
	public List<Phone> find() {
		log.debug("Into find on Adapter MariaDB");
		return phoneRepositoryMaria.findAll().stream().map(phoneMapperMaria::fromAdapterToDomain)
				.collect(Collectors.toList());
	}

	@Override
	public Phone findById(String identification) {
		log.debug("Into findById on Adapter MariaDB");
		if (phoneRepositoryMaria.findById(identification).isEmpty()) {
			return null;
		} else {
			return phoneMapperMaria.fromAdapterToDomain(phoneRepositoryMaria.findById(identification).get());
		}
	}

}
