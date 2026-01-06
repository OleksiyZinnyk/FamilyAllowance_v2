package studentorder.validator.register;

import studentorder.domain.Person;
import studentorder.domain.register.CityRegisterResponse;
import studentorder.exception.CityRegisterException;
import studentorder.exception.TransportException;

public class RealCityRegisterChecker implements CityRegisterChecker {
    public CityRegisterResponse checkPerson(Person person) throws CityRegisterException, TransportException {
        return null;
    }
}
