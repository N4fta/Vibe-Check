describe("Testing likes on a post", () => {
  before(() => {
    cy.visit(`/login`);

    cy.get('[data-cy="submit-register-tab"]').click();

    cy.get('[data-cy="input-register-username"]').type("johnny");
    cy.get('[data-cy="input-register-email"]').type("john@doe.com");
    cy.get('[data-cy="input-register-birthdate"]').type("2011-11-11");
    cy.get('[data-cy="input-register-password"]').type("password");
    cy.get('[data-cy="input-register-password2"]').type("password");
    cy.get('[data-cy="submit-register"]').click();

    cy.get('[data-cy="submit-login-tab"]').click();
    cy.get('[data-cy="submit-login"]').click();

    cy.url().should("include", "/");
    cy.get('[data-cy="submit-login"]').should("not.exist"); // Waits until this element does not exist in DOM
  });

  it("Like a post", () => {
    cy.get('[data-cy="submit-like"]').first().click();
    cy.get('[data-cy="submit-unlike"]');
  });
});
