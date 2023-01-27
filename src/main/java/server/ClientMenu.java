package server;

import Main.TransferObject;
import entity.*;


import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import enums.*;

import service.Impl.*;

public class ClientMenu {
    private ObjectInputStream inputStream = null;
    private ObjectOutputStream outputStream = null;

    public ClientMenu(ObjectInputStream inputStream, ObjectOutputStream outputStream) {
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    public <T> boolean startClientMenu() {
        TransferObject transferObject = new TransferObject();
        SerivceFather service = null;
        while (true) {

            transferObject = transferObject.getObjectFrom(inputStream);
            service = getService(transferObject.getObject());

            switch (transferObject.getCommand()) {
                case ADD_APIVES:{
                    Apives active = (Apives) transferObject.getObject();
                    Apives passive = (Apives) transferObject.getObjectFrom(inputStream).getObject();
                    Company company = (Company) transferObject.getObjectFrom(inputStream).getObject();
                    Currency currency = (Currency) transferObject.getObjectFrom(inputStream).getObject();

                    active.setCompany(company);
                    passive.setCompany(company);
                    int id = service.addObject(active);
                    active.setOperationId(id);
                    id = service.addObject(passive);
                    passive.setOperationId(id);
                    List<Apives> apives = new ArrayList<>();
                    apives.add(active);
                    apives.add(passive);
                    transferObject.sendList(apives, Commands.ADD_APIVES, outputStream);
                    LiquidityServiceImpl lsi = new LiquidityServiceImpl();
                    Liquidity liquidity = lsi.getLiquidity(currency, active, passive);
                    id = lsi.addObject(liquidity);
                    liquidity.setOperationId(id);
                    transferObject.sendObjectTo(liquidity, Commands.ADD_APIVES, outputStream);


                }
                break;
                case UPDATE_APIVES:{
                    Apives active = (Apives) transferObject.getObject();
                    Apives passive = (Apives) transferObject.getObjectFrom(inputStream).getObject();
                    Currency currency = (Currency) transferObject.getObjectFrom(inputStream).getObject();
                    Company company = (Company) transferObject.getObjectFrom(inputStream).getObject();

                    active.setCompany(company);
                    passive.setCompany(company);
                    service.updateObject(active);
                    service.updateObject(passive);
                    LiquidityServiceImpl lsi = new LiquidityServiceImpl();
                    List<Liquidity> liquidityList = lsi.readAll();
                    Liquidity liquidity = new Liquidity();
                    for(Liquidity liquidity1: liquidityList){
                        if(company.getName().equals(liquidity1.getCompany().getName()) &&
                        active.getYear() == liquidity1.getYear()){
                            liquidity = liquidity1;
                            break;
                        }
                    }
                    int id = liquidity.getOperationId();

                    liquidity = lsi.getLiquidity(currency, active, passive);

                    liquidity.setOperationId(id);

                    lsi.updateObject(liquidity);

                    transferObject.sendObjectTo(liquidity, Commands.UPDATE_APIVES, outputStream);
                }
                    break;
                case RECORD:
                    service.recordObject(transferObject.getObject());
                    break;
                case LIQUIDITY:{
                    Company company = (Company) transferObject.getObject();
                    LiquidityServiceImpl lsi = new LiquidityServiceImpl();
                    ApivesServiceImpl asi = new ApivesServiceImpl();
                    List<Apives> apivesList = asi.ReadCompany(company.getCompanyId());
                    transferObject.sendList(apivesList, Commands.LIQUIDITY, outputStream);
                    List<Liquidity> liquidityList = lsi.ReadCompany(company.getCompanyId());
                    transferObject.sendList(liquidityList, Commands.LIQUIDITY, outputStream);

                    CurrencyServiceImpl csi = new CurrencyServiceImpl();
                    List<Currency> currencyList = new ArrayList<>();
                    for(Liquidity liquidity: liquidityList){
                        currencyList.add(csi.readObject(liquidity.getCurrency().getCurrencyId()));
                    }
                    transferObject.sendList(currencyList, Commands.LIQUIDITY, outputStream);
                }
                    break;
                case PROFIT:{
                    Company company = (Company) transferObject.getObject();
                    ProfitServiceImpl psi = new ProfitServiceImpl();
                    CapitalServiceImpl csi = new CapitalServiceImpl();
                    List<Capital> capitalList = csi.ReadCompany(company.getCompanyId());
                    transferObject.sendList(capitalList, Commands.PROFIT, outputStream);
                    List<Profit> profitList = psi.ReadCompany(company.getCompanyId());
                    transferObject.sendList(profitList, Commands.PROFIT, outputStream);
                }
                break;
                case ADD_CURRENCY:{
                    Currency currency = (Currency) transferObject.getObject();
                    int id = service.addObject(currency);
                    currency.setCurrencyId(id);
                    transferObject.sendObjectTo(currency, Commands.ADD_CURRENCY, outputStream);
                }
                    break;
                case CAPITALS_UPDATE:{
                    Capital capital = (Capital) transferObject.getObject();
                    Company company = (Company) transferObject.getObjectFrom(inputStream).getObject();

                    capital.setCompany(company);
                    for(Capital capital1:(List<Capital>) service.readAll()) {
                        if (capital1.getYear() == capital.getYear()
                                && capital1.getCompany().getCompanyId() == company.getCompanyId()) {
                            capital.setOperationId(capital1.getOperationId());
                            break;
                        }
                    }
                    service.updateObject(capital);

                    ProfitServiceImpl psi = new ProfitServiceImpl();

                    List<Profit> profits = psi.readAll();
                    for(Profit profit: profits){
                        if(profit.getYear() == capital.getYear() &&
                                profit.getCompany().getCompanyId() == company.getCompanyId()){
                            int id = profit.getOperationId();
                            profit = psi.getProfit(capital);
                            profit.setOperationId(id);
                            profit.setCompany(company);
                            psi.updateObject(profit);
                            transferObject.sendObjectTo(profit, Commands.CAPITALS_UPDATE, outputStream);
                            break;
                        }
                    }
                }
                    break;
                case UPDATE_COMPANY:{
                    Company company = (Company) transferObject.getObject();
                    transferObject = transferObject.getObjectFrom(inputStream);
                    company.setUser((User) transferObject.getObject());
                    service.updateObject(company);
                }
                    break;
                case UPDATE:
                    service.updateObject(transferObject.getObject());
                    break;
                case isLoginEXIST: {
                    Commands cmd = Commands.ERROR;
                    User usr = isLoginExist(transferObject);
                    if (usr != null) {
                        cmd = Commands.isEXIST;
                    }
                    transferObject.sendObjectTo(usr, cmd, outputStream);
                }
                    break;
                case NEWCOMPANY:
                    ClientMenuAddCompany(transferObject);
                    break;
                case REGISTER:
                    ClientMenuRegister(transferObject);
                    break;
                case isEXIST: {
                    Commands cmd = Commands.ERROR;
                    User usr = isExist(transferObject);
                    if (usr != null) {
                        cmd = Commands.isEXIST;
                    }
                    transferObject.sendObjectTo(usr, cmd, outputStream);
                }
                    break;
                case ADD:
                    if (service.addObject(transferObject.getObject()) == 0) {
                        System.out.println("Object was not added");
                    }
                    break;
                case DELETE:
                    if (!service.deleteObject(transferObject.getObject())) {
                        System.out.println("Object was not deleted");
                    }
                    break;
                case READ_ALL:
                    List<T> list = service.readAll();
                    transferObject.sendList(list, Commands.READ_ALL, outputStream);
                    break;
                case CAPITALS:
                    ClientMenuCapitals(transferObject);
                    break;
            }
        }
    }
    private void ClientMenuAddCompany(TransferObject transferObject) {
        Company company = (Company)transferObject.getObject();
        User user = (User) transferObject.getObjectFrom(inputStream).getObject();
        CompanyServiceImpl csi = new CompanyServiceImpl();
        company.setUser(user);

        company.setCompanyId(csi.addObject(company));
        transferObject.sendObjectTo(company, Commands.NEWCOMPANY, outputStream);


    }
    private void ClientMenuRegister(TransferObject transferObject) {
        try {
            User user = (User) transferObject.getObject();
            UserServiceImpl usi = new UserServiceImpl();
            int id = usi.addObject(user);
            user.setUserId(id);
            System.out.println(id);

            Company company = (Company) transferObject.getObjectFrom(inputStream).getObject();
            company.setUser(user);
            CompanyServiceImpl csi = new CompanyServiceImpl();
            csi.addObject(company);

            TransferObject to = new TransferObject<>(user, Commands.ERROR);
            to.setObject(user);
            user = isExist(to);
            to.setObject(null);
            transferObject.sendObjectTo(user, Commands.REGISTER, outputStream);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private void ClientMenuCapitals(TransferObject transferObject) {
        Company company = (Company) transferObject.getObject();
        TransferObject capital = transferObject.getObjectFrom(inputStream);
        ProfitServiceImpl psi = new ProfitServiceImpl();
        CapitalServiceImpl csi = new CapitalServiceImpl();
        Capital obj = (Capital) capital.getObject();
        obj.setCompany(company);
        csi.addObject(obj);
        Profit profit = psi.getProfit(obj);
        psi.addObject(profit);
        transferObject.sendObjectTo(profit, Commands.PROFIT, outputStream);
    }
    private User isExist(TransferObject transferObject) {
        UserServiceImpl usi = new UserServiceImpl();
        User user = usi.isExist((User) transferObject.getObject());
        return user;
    }
    private User isLoginExist(TransferObject transferObject) {
        UserServiceImpl usi = new UserServiceImpl();
        User user = usi.isLoginExist((User) transferObject.getObject());
        return user;
    }
    private <T> SerivceFather getService(T object) {
        SerivceFather service = null;
        if (object instanceof Apives) {
            service = new ApivesServiceImpl();
        } else if (object instanceof Capital) {
            service = new CapitalServiceImpl();
        } else if (object instanceof Company) {
            service = new CompanyServiceImpl();
        } else if (object instanceof Currency) {
            service = new CurrencyServiceImpl();
        } else if (object instanceof Liquidity) {
            service = new LiquidityServiceImpl();
        } else if (object instanceof Profit) {
            service = new ProfitServiceImpl();
        } else if (object instanceof User) {
            service = new UserServiceImpl();
        }
        return service;
    }
}
