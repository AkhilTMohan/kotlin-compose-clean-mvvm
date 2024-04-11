package com.interview.planets

import com.interview.planets.data.PlanetRepositoryTest
import com.interview.planets.domain.PlanetUseCaseTest
import com.interview.planets.presentation.MainViewModelTest
import org.junit.platform.suite.api.SelectClasses
import org.junit.platform.suite.api.Suite


@Suite
@SelectClasses(
    MainViewModelTest::class,
    PlanetUseCaseTest::class,
    PlanetRepositoryTest::class
)
class Suit