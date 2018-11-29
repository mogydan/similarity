package com.mogydan.similarity.controller;

import com.mogydan.similarity.exception.ResourceNotFoundException;
import com.mogydan.similarity.model.Customer;
import com.mogydan.similarity.service.CustomerService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
@Api(value = "Customers", description = "Customers controller", tags = {"Customers"})
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    @ApiOperation(value = "Add new customer", response = Customer.class, tags = {"Customers"})
    @ApiResponses(
            value = {
                    @ApiResponse(code = 201, message = "OK"),
                    @ApiResponse(code = 400, message = "unexpected error", response = ResourceNotFoundException.class)
            }
    )
    public Customer createCustomer(
            @ApiParam(value = "Name of new customer", required = true) @Valid @RequestParam("name") String name,
            @ApiParam(value = "Email of new customer", required = true) @Valid @RequestParam("email") String email
    ) {
        return customerService.createCustomer(
                new Customer()
                        .setName(name)
                        .setEmail(email)
        );
    }

    @PostMapping("/addAll")
    @ApiOperation(value = "Add the list of customers to the store", tags = {"Customers"})
    @ApiResponses(
            value = {
                    @ApiResponse(code = 201, message = "Ok"),
                    @ApiResponse(code = 400, message = "unexpected error", response = ResourceNotFoundException.class)
            }
    )
    public void addCustomers(
            @ApiParam(value = "List of customers", required = true) @Valid @RequestBody List<Customer> customers
    ) {
        customerService.addCustomers(customers);
    }

    @GetMapping("/{customerId}")
    @ApiOperation(value = "Info for a specific customer", response = Customer.class, tags = {"Customers"})
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Ok", response = Customer.class),
                    @ApiResponse(code = 400, message = "unexpected error", response = ResourceNotFoundException.class)
            }
    )
    public Customer getCustomer(
            @ApiParam(value = "The id of the customer to retrieve", required = true) @PathVariable long customerId
    ) {
        return customerService.getCustomer(customerId);
    }

    @GetMapping
    @ApiOperation(
            value = "The list of all customers", response = Customer.class,
            responseContainer = "List",
            tags = {"Customers"}
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            code = 200,
                            message = "List of all customers",
                            response = Customer.class,
                            responseContainer = "List"
                    ),
                    @ApiResponse(code = 200, message = "Unexpected error", response = ResourceNotFoundException.class)
            })
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @PutMapping("/{customerId}")
    @ApiOperation(value = "Update customer", tags = {"Customers"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 400, message = "unexpected error", response = ResourceNotFoundException.class)
    })
    public void updateCustomer(
            @ApiParam(value = "The id of the customer to update", required = true) @PathVariable long customerId,
            @ApiParam(value = "Body of updated customer", required = true) @Valid @RequestBody Customer customer
    ) {
        customerService.updateCustomer(customer, customerId);
    }

    @DeleteMapping("/{customerId}")
    @ApiOperation(value = "Delete customer by Id", tags = {"Customers"})
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Ok"),
                    @ApiResponse(code = 400, message = "unexpected error", response = ResourceNotFoundException.class)
            }
    )
    public void deleteCustomerById(
            @ApiParam(value = "The id of the customer to delete", required = true) @PathVariable long customerId
    ) {
        customerService.deleteCustomer(customerId);
    }
}