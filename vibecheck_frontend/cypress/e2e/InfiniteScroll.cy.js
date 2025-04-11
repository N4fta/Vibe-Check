describe("Testing infinite scroll", () => {
  before("Login in", () => {
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

  before("Create posts", () => {
    for (let index = 0; index < 15; index++) {
      cy.get('[data-cy="submit-new-post-dialogue"]').click();

      cy.get('[data-cy="input-new-post-title"]').type("test title");
      cy.get('[data-cy="input-new-post-body"]').type("test body");
      cy.get('[data-cy="input-new-post-song"]').type("fffire");
      cy.get('[data-cy="input-new-post-song-select"]').first().click();
      cy.get('[data-cy="submit-new-post"]').click();

      cy.get('[data-cy="label-new-post-in-feed"]'); // Checks new element exists
    }
  });

  it("Like a post", () => {
    cy.get('[data-cy="label-last-post"]').scrollIntoView();
    cy.wait(2000);
    cy.get('[data-cy="label-last-post"]').should("not.be.visible");
  });
});
