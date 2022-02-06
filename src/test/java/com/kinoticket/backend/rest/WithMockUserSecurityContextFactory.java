package com.kinoticket.backend.rest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.kinoticket.backend.model.Booking;
import com.kinoticket.backend.model.ERole;
import com.kinoticket.backend.model.Role;
import com.kinoticket.backend.model.User;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

final class WithMockUserSecurityContextFactory implements WithSecurityContextFactory<WithMockUser> {

	@Override
	public SecurityContext createSecurityContext(WithMockUser withUser) {
		String username = StringUtils.hasLength(withUser.username()) ? withUser.username() : withUser.value();
		Assert.notNull(username, () -> withUser + " cannot have null username on both username and value properties");
		
		
		User principal = new User(username, withUser.password());

		ArrayList<Booking> bookings = new ArrayList<>();
		bookings.add(BookingControllerTests.createBooking());
		principal.setBookings(bookings);

        Set<Role> roles = new HashSet<>();
		roles.add(new Role(ERole.ROLE_USER));

		principal.setRoles(roles);

		Authentication authentication = new UsernamePasswordAuthenticationToken(principal, principal.getPassword(),
				principal.getAuthorities());
		SecurityContext context = SecurityContextHolder.createEmptyContext();
		context.setAuthentication(authentication);
		return context;
	}

}
