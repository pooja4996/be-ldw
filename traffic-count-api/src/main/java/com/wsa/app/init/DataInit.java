package com.wsa.app.init;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.wsa.app.domain.Lock;
import com.wsa.app.domain.Roles;
import com.wsa.app.domain.Ship;
import com.wsa.app.domain.User;
import com.wsa.app.repository.LockRepository;
import com.wsa.app.repository.ShipRepository;
import com.wsa.app.repository.UserRepository;

@Configuration
public class DataInit {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private LockRepository lockRepo;

	@Autowired
	private ShipRepository shipRepo;

	@Autowired
	private PasswordEncoder encoder;

	private final Logger log = LoggerFactory.getLogger(getClass());

	@EventListener(ApplicationReadyEvent.class)
	private void init() throws IOException {
    /*
		userRepo.deleteAll();
		User user = new User();
		user.setUserId(1001);
		user.setUsername("WSA");
		user.setPassword(encoder.encode("admin@123"));
		user.setFirstName("Nutzer");
		user.setLastName("");
		user.setRole(Roles.ADMIN);
		userRepo.save(user);
		
		lockRepo.deleteAll();
		List<Lock> locks = getAllLocks();
		lockRepo.saveAll(locks);

		shipRepo.deleteAll();
		List<Ship> ships = getAllShips();
		shipRepo.saveAll(ships);*/
	}

	private List<Ship> getAllShips() throws IOException {
		List<Ship> ships = new ArrayList<>();
		Resource resource = new ClassPathResource("data/CountingShipsInLocks.xlsx");
		FileInputStream fis = new FileInputStream(resource.getFile());
		Workbook wb = new XSSFWorkbook(fis);
		Sheet shipSheet = wb.getSheetAt(1);
		Iterator<Row> rows = shipSheet.iterator();
		int rowNumber = 0;
		while (rows.hasNext()) {
			Row currentRow = rows.next();

			// skip header
			if (rowNumber == 0) {
				rowNumber++;
				continue;
			}
			String startingDate = new SimpleDateFormat("yyyy-MM-dd").format(currentRow.getCell(0).getDateCellValue());
			String startingTime = new SimpleDateFormat("HH:mm:ss").format(currentRow.getCell(1).getDateCellValue());
			Ship ship = new Ship();
			ship.setShippedTime(LocalDateTime.of(LocalDate.parse(startingDate), LocalTime.parse(startingTime)));
			ship.setDirection(currentRow.getCell(2).getStringCellValue());
			ship.setLock(
					lockRepo.findById((long) currentRow.getCell(3).getNumericCellValue() + "".trim()).orElse(null));
			ship.setCategory(currentRow.getCell(11).getStringCellValue());
			ships.add(ship);

		}
		log.info("{Ships Info saved successfully !!}");
		wb.close();
		return ships;
	}

	private List<Lock> getAllLocks() throws IOException {
		List<Lock> locks = new ArrayList<>();
		Resource resource = new ClassPathResource("data/CountingShipsInLocks.xlsx");
		FileInputStream fis = new FileInputStream(resource.getFile());
		Workbook wb = new XSSFWorkbook(fis);
		Sheet lockSheet = wb.getSheetAt(0);
		Iterator<Row> rows = lockSheet.iterator();
		int rowNumber = 0;
		while (rows.hasNext()) {
			Row currentRow = rows.next();

			// skip header
			if (rowNumber == 0) {
				rowNumber++;
				continue;
			}
			Lock lock = new Lock();
			lock.setId((long) currentRow.getCell(0).getNumericCellValue() + "".trim());
			lock.setLongitude(currentRow.getCell(1).getNumericCellValue());
			lock.setLatitude(currentRow.getCell(2).getNumericCellValue());
			lock.setAdminOffice(currentRow.getCell(3).getStringCellValue());
			lock.setSecondLevelAdmin(currentRow.getCell(4).getStringCellValue());
			lock.setLockName(currentRow.getCell(5).getStringCellValue());
			lock.setWaterway(currentRow.getCell(6).getStringCellValue());
			lock.setYear((long) currentRow.getCell(7).getNumericCellValue());
			try {
				lock.setWebcamUrl(currentRow.getCell(8).getStringCellValue());
			} catch (NullPointerException ex) {
				lock.setWebcamUrl(null);
			}
			try {
				lock.setImageUrl(currentRow.getCell(9).getStringCellValue());
				lock.setImageLicence(currentRow.getCell(10).getStringCellValue());
				lock.setImageOwner(currentRow.getCell(11).getStringCellValue());
			} catch (NullPointerException ex) {
				lock.setImageUrl(null);
				lock.setImageLicence(null);
				lock.setImageOwner(null);
			}

			locks.add(lock);
		}
		log.info("{Locks Info saved successfully !!}");
		wb.close();
		return locks;
	}
}
